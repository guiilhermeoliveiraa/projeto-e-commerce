package com.javacore.spring_api_app.service.sendgrid;

import com.javacore.spring_api_app.dto.request.sendgrid.SendGridRequest;
import com.javacore.spring_api_app.exception.custom.BusinessException;
import com.javacore.spring_api_app.properties.sendgrid.SendGridProperties;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class SendGridServiceImpl implements SendGridService{

    private final SendGridProperties properties;
    private final SendGrid sendGrid;

    public SendGridServiceImpl(SendGridProperties properties, SendGrid sendGrid) {
        this.properties = properties;
        this.sendGrid = sendGrid;
    }

    @Override
    @Transactional
    public void sendEmail(SendGridRequest request) {
        Email from = new Email(properties.fromEmail());
        Email toEmail = new Email(request.to());

        Content content = new Content("text/html", request.message());

        Mail mail = new Mail(from, request.subject(), toEmail, content);

        Request sdRequest = new Request();

        try {
            sdRequest.setMethod(Method.POST);
            sdRequest.setEndpoint("mail/send");
            sdRequest.setBody(mail.build());

            Response response = sendGrid.api(sdRequest);

            int statusCode = response.getStatusCode();

            if (statusCode != 202) {
                String responseBody = response.getBody();
                throw new BusinessException("Erro ao enviar email. Status: " + statusCode + " Body: " + responseBody);
            }
        } catch (IOException ex) {
            throw new BusinessException("Ocorreu um erro ao enviar email de verificação");
        }
    }
}