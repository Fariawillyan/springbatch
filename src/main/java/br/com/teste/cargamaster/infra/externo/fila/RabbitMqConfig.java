/*package br.com.teste.cargamaster.infra.message.fila;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RabbitMqConfig {

    private static final String NAME_EXCHANGE = "amq.direct";
    @Value("${spring.rabbit.queue.execucao}")
    private String execucao;

    @Value("${spring.rabbit.queue.resposta.execucao}")
    private String respostaExecucao;

    @Autowired
    private AmqpAdmin amqpAdmin;

    private Queue queue(String queueName){
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange(){
        return new DirectExchange(NAME_EXCHANGE);
    }

    private Binding relationship(Queue queue, DirectExchange directExchange){
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void addQueue(){

        Queue queueExecucao = this.queue(execucao);
        Queue queueRespostaExecucao = this.queue(respostaExecucao);

        Binding execucao = this.relationship(queueExecucao, this.directExchange());
        Binding respostaExecucao = relationship(queueRespostaExecucao, this.directExchange());

        this.amqpAdmin.declareQueue(queueExecucao);
        this.amqpAdmin.declareQueue(queueRespostaExecucao);

        this.amqpAdmin.declareExchange(this.directExchange());

        this.amqpAdmin.declareBinding(execucao);
        this.amqpAdmin.declareBinding(respostaExecucao);
    }
}
*/
