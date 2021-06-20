package com.activemq.produtos.consumer;

import com.activemq.produtos.constants.ProdutosConstants;
import com.activemq.produtos.model.Produto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {ProdutosConstants.PRODUTOS_QUEUE})
@Slf4j
public class ProdutoConsumerRabbitListener {

    private RabbitTemplate rabbit;

    @Autowired
    public ProdutoConsumerRabbitListener(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @RabbitHandler
    public void consumerProduto(Produto produto) {
        try {
            log.info("<<Produto recebido>>: {}", produto.toString());
        } catch (Exception e) {
            log.error("<<Erro no recebimento>>: {}",e);
        }
    }

}
