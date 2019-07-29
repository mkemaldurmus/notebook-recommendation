package com.exastax.notebook.commons;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : Baran YILMAZ
 * @since : 29.07.2019
 **/
public class ConnectRabbit {
    public Channel getChannel(String ip, Integer port, String username,
                              String password, String virtualHost
    ) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ip);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(10000);

        return factory.newConnection().createChannel();
    }
}