/*
package br.com.teste.cargamaster.infra.message.fila;

import br.com.teste.cargamaster.domain.motor.PreJob.vo.ExecutarPreJob;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public ResponseEntity<Object> tryAgain(ExecutarPreJob executarPreJob){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(executarPreJob);

            //rabbitTemplate.convertAndSend("execucao", json);

        } catch (AmqpException e) {
            log.debug("Failed to send message to RabbitMQ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending message to RabbitMQ");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        final boolean exec = true;

        return exec ?
                ResponseEntity.status(HttpStatus.OK).body("Processando execução! ") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERRO ao processar execução");
    }

}
*/
