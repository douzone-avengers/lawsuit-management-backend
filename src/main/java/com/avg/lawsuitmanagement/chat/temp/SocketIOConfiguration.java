package com.avg.lawsuitmanagement.chat.temp;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SocketIOConfiguration {

    @Value("${socket.host}")
    private String host;
    @Value("${socket.port}")
    private int port;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);

        SocketIOServer server = new SocketIOServer(config);
        server.start();

        server.addConnectListener(handleConnect());
        server.addDisconnectListener(handleDisconnect());

        return server;
    }

    private ConnectListener handleConnect() {
        return client -> {
            log.info("new user connected with socket: {}", client.getSessionId());
        };
    }

    private DisconnectListener handleDisconnect() {
        return client -> {
            client.getNamespace().getAllClients()
                .forEach(socketIOClient -> {
                    log.info("user disconnected: {}", socketIOClient.getSessionId().toString());
                });
        };
    }

    @PreDestroy
    public void stopSocketIOServer() {
        this.socketIOServer().stop();
    }

}
