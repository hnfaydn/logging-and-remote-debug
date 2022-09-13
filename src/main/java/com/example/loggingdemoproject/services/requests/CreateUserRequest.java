package com.example.loggingdemoproject.services.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

  private String firstName;

  private String lastName;

  private String email;
}
