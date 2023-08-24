package mx.edu.utez.secondapp.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmailService {
    private ResourceBundle bundle;
    private String email;
    private String password;
    private String host;
    private String port;
    private String enable;
    private String auth;

    public boolean sendMail(String username) {
        if (bundle == null) {
            bundle = ResourceBundle
                    .getBundle("email_props");
            email = bundle.getString("email");
            password = bundle.getString("password");
            host = bundle.getString("host");
            port = bundle.getString("port");
            enable = bundle.getString("enable");
            auth = bundle.getString("auth");
        }
        System.out.println(email);
        System.out.println(password);
        System.out.println(host);
        System.out.println(port);
        System.out.println(enable);
        System.out.println(auth);
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", enable);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        try {
            Session session = Session.getInstance(
                    props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(email, password);
                        }
                    }
            );
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(
                    email, "PK | Usuario creado"
            ));//Opcional
            message.setRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(username)
            );
            message.setSubject("Registro éxitoso");
            message.setContent("<strong>" +
                    "Correo enviado desde servlets" +
                    "</strong>", "text/html");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;

    }
}
