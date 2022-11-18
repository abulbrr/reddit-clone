package com.habbal.redditclone.service;

import com.habbal.redditclone.exception.SpringRedditException;
import com.habbal.redditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Value("${api.email}")
    private String redditCloneApi;

    @Value("${api.version}")
    private String apiVersion;

    @Async
    void sendAccountActivationEmail(String email, String token) {
        var notificationEmail = new NotificationEmail(
                "Please Activate your Account",
                email,
                mailContentBuilder.build("Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/" + apiVersion + "/auth/accountVerification/" + token));

        sendMail(notificationEmail);
    }
    @Async
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(redditCloneApi);
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };

        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }

    }
}
