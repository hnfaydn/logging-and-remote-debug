package com.example.loggingdemoproject.outservices.mailService;

import org.springframework.stereotype.Service;

@Service
public class EMailVerificationImpl implements EMailVerificationService {
  @Override
  public boolean eMailVerification(final String email) {
    return true;
  }
}
