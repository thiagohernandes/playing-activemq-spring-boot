package com.activemq.produtos.producer;

import com.activemq.produtos.constants.ProdutosConstants;
import com.activemq.produtos.model.Produto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
public class ProdutoProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(fixedRate = 2000L)
    public void producer(){
        try {
            Random random = new Random();
            Produto produto = new Produto(random.nextInt(), "Produto " + UUID.randomUUID(), random.nextInt());
            rabbitTemplate.convertAndSend(ProdutosConstants.PRODUTOS_EXCHANGE,
                    ProdutosConstants.PRODUTOS_ROUTING_KEY,
                    produto, new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message)
                                throws AmqpException {
                            MessageProperties props = message.getMessageProperties();
                            props.setHeader("X_ORDER_SOURCE", "WEB");
                            return message;
                        }
                    });
            log.info("**Produto enviado**: " + produto.toString());
        } catch (Exception e) {
            log.error("**Erro no envio**: {} ", e);
        }
    }

}
