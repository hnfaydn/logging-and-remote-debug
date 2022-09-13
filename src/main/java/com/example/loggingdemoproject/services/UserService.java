package com.example.loggingdemoproject.services;

import com.example.loggingdemoproject.entities.User;
import com.example.loggingdemoproject.services.requests.CreateUserRequest;
import com.example.loggingdemoproject.utilities.results.Result;

public interface UserService {

  Result add(CreateUserRequest createUserRequest) throws Exception;

  Result getAll();
}
