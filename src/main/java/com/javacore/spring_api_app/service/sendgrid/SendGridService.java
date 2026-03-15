package com.javacore.spring_api_app.service.sendgrid;

import com.javacore.spring_api_app.dto.request.sendgrid.SendGridRequest;

public interface SendGridService {
    void sendEmail(SendGridRequest request);
}
