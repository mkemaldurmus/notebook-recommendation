package com.exastax.notebook.consumer;

import com.exastax.notebook.commons.RabbitConnect;
import com.exastax.notebook.constants.RabbitConst;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : Baran YILMAZ
 * @since : 29.07.2019
 **/
public class DataConsumerMain implements RabbitConst {
    private static final String QUEUE_NAME = "geobanking_building_worker";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = new RabbitConnect().getChannel(HOST,PORT,USERNAME,PASSWORD,VIRTUALHOST);
        channel.basicQos(200);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                String URL = new String(body);

                try{

                }catch (Exception e){

                }
            }
        };
    }
}
