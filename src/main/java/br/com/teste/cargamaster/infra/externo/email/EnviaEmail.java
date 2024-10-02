package br.com.teste.cargamaster.infra.externo.email;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EnviaEmail {
    private final JavaMailSender mailSender;

    public EnviaEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(SimpleMailMessage message){
        mailSender.send(message);
    }

}
