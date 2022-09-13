package com.example.loggingdemoproject.services;

import com.example.loggingdemoproject.dataAccess.UserDao;
import com.example.loggingdemoproject.entities.User;
import com.example.loggingdemoproject.outservices.mailService.EMailVerificationService;
import com.example.loggingdemoproject.outservices.userVerificationService.UserValidationService;
import com.example.loggingdemoproject.services.requests.CreateUserRequest;
import com.example.loggingdemoproject.utilities.results.ErrorResult;
import com.example.loggingdemoproject.utilities.results.Result;
import com.example.loggingdemoproject.utilities.results.SuccessDataResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserImpl implements UserService {
  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  private final UserDao userDao;
  private final EMailVerificationService eMailVerificationService;
  private final UserValidationService userValidationService;

  @Override
  public Result add(final CreateUserRequest createUserRequest) {
    try {
      userFirstNamelessThan2Letter(createUserRequest.getFirstName());
      userLastNamelessThan2Letter(createUserRequest.getLastName());
      emailPatternVerification(createUserRequest.getEmail());
      log.info(
          "Following email {} has sent to email verification service.",
          createUserRequest.getEmail());
      eMailVerification(createUserRequest.getEmail());
      log.info("Following email {} has verified.", createUserRequest.getEmail());
      User user =
          User.builder()
              .id(UUID.randomUUID().toString())
              .firstName(createUserRequest.getFirstName())
              .lastName(createUserRequest.getLastName())
              .email(createUserRequest.getEmail())
              .build();
      log.info("Following User id {} has sent to User verification service.", user.getId());
      userValidation(user);
      log.info("Following User id {} has verified.", user.getId());
      userDao.save(user);
      log.info(
          "{} {} with following user id {} has been saved to database.",
          user.getFirstName(),
          user.getLastName(),
          user.getId());
    } catch (Exception e) {
      log.error("An error occurred: {}", e.getMessage(), e);
      return new ErrorResult(e.getMessage());
    }
    return new SuccessDataResult(createUserRequest, "User Added Successfully.");
  }

  @Override
  public Result getAll() {
    return new SuccessDataResult(userDao.findAll(), "All users listed");
  }

  private void userValidation(User user) throws Exception {
    if (!userValidationService.userVerification(user)) {
      throw new Exception("User could not verified.");
    }
  }

  private void emailPatternVerification(final String email) throws Exception {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    if (!matcher.find()) {
      throw new Exception("Email format is not correct.");
    }
  }

  private void userLastNamelessThan2Letter(final String lastName) throws Exception {
    if (lastName.length() < 2) {
      throw new Exception("User last name can not less than 2 letter");
    }
  }

  private void userFirstNamelessThan2Letter(final String firstName) throws Exception {
    if (firstName.length() < 2) {
      throw new Exception("User first name can not less than 2 letter");
    }
  }

  private void eMailVerification(final String email) throws Exception {
    if (!eMailVerificationService.eMailVerification(email)) {
      throw new Exception("Email could not verified.");
    }
  }
}
