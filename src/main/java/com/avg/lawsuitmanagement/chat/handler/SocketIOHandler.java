package com.avg.lawsuitmanagement.chat.handler;

import com.avg.lawsuitmanagement.chat.dto.MessageCreateParam;
import com.avg.lawsuitmanagement.chat.dto.MessageRawWithRead;
import com.avg.lawsuitmanagement.chat.dto.MessageSendRequest;
import com.avg.lawsuitmanagement.chat.service.ChatService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketIOHandler {

    private ChatService chatService;

    public SocketIOHandler(ChatService chatService, SocketIOServer socketIOServer) {
        this.chatService = chatService;
        socketIOServer.addEventListener("send-message", MessageSendRequest.class,
            handleSendMessage());
    }

    private DataListener<MessageSendRequest> handleSendMessage() {
        return (client, data, ackSender) -> {
            chatService.showRoomById(data.getRoomId());
            List<MessageRawWithRead> messageRaws = chatService.saveMessage(
                MessageCreateParam.builder()
                    .roomId(data.getRoomId())
                    .senderId(data.getSenderId())
                    .content(data.getContent())
                    .build());

            for (MessageRawWithRead item : messageRaws) {
                client.getNamespace()
                    .getBroadcastOperations()
                    .sendEvent("receive-message/" + item.getReceiverId(),
                        chatService.getAllMessages(item.getRoomId()));
            }

            ackSender.sendAckData("success");
        };
    }
}
