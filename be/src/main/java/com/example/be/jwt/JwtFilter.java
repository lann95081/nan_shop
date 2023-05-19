package com.example.be.jwt;

import com.example.be.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Lấy token JWT từ request
            String jwt = parseJwt(request);

            System.out.println("Token JWT: " + jwt);

            // Nếu JWT hợp lệ thì tiến hành xác thực và cấp quyền truy cập
            if (jwt != null && jwtUtility.validateJwtToken(jwt)) {
                // Lấy thông tin username từ JWT
                String username = jwtUtility.getUserNameFromJwtToken(jwt);

                // Tải thông tin chi tiết của người dùng từ database
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                // Tạo đối tượng authentication để xác thực người dùng
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Cung cấp chi tiết của request để phục vụ cho việc xác thực
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Thực hiện xác thực và cấp quyền truy cập cho người dùng
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {

        // Lấy giá trị của tiêu đề "Authorization" trong yêu cầu HTTP đến.
        String headerAuth = request.getHeader("Authorization");

        // Kiểm tra xem giá trị của tiêu đề "Authorization" có khác rỗng và có bắt đầu bằng chuỗi "Bearer " hay không.
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // Nếu giá trị của tiêu đề "Authorization" hợp lệ, phương thức sẽ trả về
            // chuỗi JWT bắt đầu từ vị trí thứ 7 trong chuỗi.
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
