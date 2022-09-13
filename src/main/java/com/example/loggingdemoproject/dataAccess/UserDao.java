package com.example.loggingdemoproject.dataAccess;

import com.example.loggingdemoproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,String> {
}
