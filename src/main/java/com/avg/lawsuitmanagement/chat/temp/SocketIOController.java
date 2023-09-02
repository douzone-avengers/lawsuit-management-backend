package com.avg.lawsuitmanagement.chat.temp;

import com.avg.lawsuitmanagement.chat.temp.data.MessageRequest;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketIOController {

    public SocketIOController(SocketIOServer socketIOServer) {
        socketIOServer.addEventListener("message", MessageRequest.class, handleMessage());
    }

    private DataListener<MessageRequest> handleMessage() {
        return (client, request, ackSender) -> {
            log.info(request.toString());
            client.getNamespace().getBroadcastOperations()
                .sendEvent("message", request.toResponse());
        };
    }

}
