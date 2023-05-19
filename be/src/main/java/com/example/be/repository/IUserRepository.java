package com.example.be.repository;

import com.example.be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select a.* from  user as a where a.username= :email", nativeQuery = true)
    User findUserByEmail(@Param("email") String email);
}
