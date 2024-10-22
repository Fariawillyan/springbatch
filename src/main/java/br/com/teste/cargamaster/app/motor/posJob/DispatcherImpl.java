package br.com.teste.cargamaster.app.motor.posJob;


import br.com.teste.cargamaster.app.motor.posJob.vo.Email;
import br.com.teste.cargamaster.infra.externo.email.EnviaEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DispatcherImpl implements IDispatcher {

    @Autowired
    EnviaEmail enviaEmail;

    @Override
    public void processaEmailService() {

        var message = new SimpleMailMessage();
        Email email = new Email();

        //criar objetos de processamento de carga futuramente
        email.setTo("faria@farinha.com.br");
        email.setSubject("Carga Email");
        email.setBody("carga processado com sucesso");
        message.setFrom("noreply@email.com");

        message.setTo(email.getTo());//pode colocar uma lista
        message.setSubject(email.getSubject());
        message.setText(email.getBody());

        try {

            //enviaEmail.sendEmail(message); ATIVAR QUANDO TIVER SERVIDOR

            log.info("Enviando e-mail:");
            log.info("Para: " + String.join(", ", message.getTo()));
            log.info("Assunto: " + message.getSubject());
            log.info("Corpo: " + message.getText());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
