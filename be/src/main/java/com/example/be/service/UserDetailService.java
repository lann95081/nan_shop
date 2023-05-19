package com.example.be.service;

import com.example.be.model.AccountDetails;
import com.example.be.model.User;
import com.example.be.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = iUserRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        return AccountDetails.build(user);
    }

}
