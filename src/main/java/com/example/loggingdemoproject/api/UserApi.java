package com.example.loggingdemoproject.api;

import com.example.loggingdemoproject.services.UserService;
import com.example.loggingdemoproject.services.requests.CreateUserRequest;
import com.example.loggingdemoproject.utilities.results.ErrorDataResult;
import com.example.loggingdemoproject.utilities.results.Result;
import com.example.loggingdemoproject.utilities.results.SuccessDataResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserApi implements LoggingService {
  private final UserService userService;

  @PostMapping("/add")
  public Result add(@RequestBody CreateUserRequest createUserRequest) throws Exception {
    return userService.add(createUserRequest);
  }

  @GetMapping("/getall")
  public Result getAll() {
    return userService.getAll();
  }

  @Override
  public void displayReq(HttpServletRequest request, Object body) {
    StringBuilder reqMessage = new StringBuilder();
    Map<String, String> parameters = getParameters(request);

    reqMessage.append("REQUEST ");
    reqMessage.append("method = [").append(request.getMethod()).append("]");
    reqMessage.append(" path = [").append(request.getRequestURI()).append("] ");

    if (!parameters.isEmpty()) {
      reqMessage.append(" parameters = [").append(parameters).append("] ");
    }

    if (!Objects.isNull(body)) {
      reqMessage.append(" body = [").append(body).append("]");
    }

    log.info("log Request: {}", reqMessage);
  }

  @Override
  public void displayResp(HttpServletRequest request, HttpServletResponse response, Object body) {
    StringBuilder respMessage = new StringBuilder();
    Map<String, String> headers = getHeaders(response);
    respMessage.append("RESPONSE ");
    respMessage.append(" method = [").append(request.getMethod()).append("]");
    if (!headers.isEmpty()) {
      respMessage.append(" ResponseHeaders = [").append(headers).append("]");
    }
    if (body instanceof SuccessDataResult) {
      respMessage
          .append(" response body message = [")
          .append(((SuccessDataResult) body).getMessage())
          .append("]");
      respMessage
          .append(" response body data = [")
          .append(((SuccessDataResult) body).getData())
          .append("]");
    } else {
      if (body instanceof ErrorDataResult) {
        respMessage
            .append(" response body = [")
            .append(((ErrorDataResult) body).getMessage())
            .append("]");
      } else {
        respMessage.append(" response body = [").append((body)).append("]");
      }
    }
    log.info("logResponse: {}", respMessage);
  }

  private Map<String, String> getHeaders(HttpServletResponse response) {
    Map<String, String> headers = new HashMap<>();
    Collection<String> headerMap = response.getHeaderNames();
    for (String str : headerMap) {
      headers.put(str, response.getHeader(str));
    }
    return headers;
  }

  private Map<String, String> getParameters(HttpServletRequest request) {
    Map<String, String> parameters = new HashMap<>();
    Enumeration<String> params = request.getParameterNames();
    while (params.hasMoreElements()) {
      String paramName = params.nextElement();
      String paramValue = request.getParameter(paramName);
      parameters.put(paramName, paramValue);
    }
    return parameters;
  }
}
