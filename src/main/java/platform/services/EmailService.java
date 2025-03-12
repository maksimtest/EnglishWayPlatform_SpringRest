package platform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import platform.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ResourceService resourceService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${email.email-dir}")
    private String emailDir;

    @Value("${email.from}")
    private String emailFromAddress;
    private final String activateUrl = "/activate?code=";
    private final String changePasswordUrl = "/change?code=";

    public void sendCodeAfterRegistration(User user, String code) throws IOException {
        String subject = "Activation code English way platform";
        String text = resourceService.readFile("emails/activation-email.txt");
        String url = frontendUrl + activateUrl + code;
        String textBody = String.format(text, user.getName(), url);
        send(user.getEmail(), subject, textBody);
    }

    public void sendCodeAfterRememberPassword(User user, String code) throws IOException {
        String subject = "Reset password on English way platform";
        String text = resourceService.readFile("emails/change-password-email.txt");
        String url = frontendUrl + changePasswordUrl + code;
        String textBody = String.format(text, user.getName(), url);
        send(user.getEmail(), subject, textBody);
    }

    private void send(String toEmail, String subject, String text) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-hh-mm-ss");
        String name = "email-" + dateFormat.format(new Date()) + ".txt";
        File file = new File(emailDir, name);

        FileWriter writer = new FileWriter(file);
        writer.write("Email to: " + toEmail + "\n");
        writer.write("Email from: " + emailFromAddress + "\n");
        writer.write("Subject of email: " + subject + "\n");
        writer.write("-------------------------\n");
        writer.write("text:\n");
        writer.write(text);
        writer.close();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(text);
//        message.setFrom(emailFromAddress);
//
//        mailSender.send(message);
    }
}
