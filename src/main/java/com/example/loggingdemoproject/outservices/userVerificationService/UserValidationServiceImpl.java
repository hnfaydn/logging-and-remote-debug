package com.example.loggingdemoproject.outservices.userVerificationService;

import com.example.loggingdemoproject.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserValidationServiceImpl implements UserValidationService {
  @Override
  public boolean userVerification(final User user) {
    return true;
  }
}
