package com.example.loggingdemoproject.outservices.userVerificationService;

import com.example.loggingdemoproject.entities.User;

public interface UserValidationService {
  boolean userVerification(User user);
}
