package com.example.loggingdemoproject.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingService {

    void displayReq(HttpServletRequest request, Object body);

    void displayResp(HttpServletRequest request, HttpServletResponse response, Object body);
}