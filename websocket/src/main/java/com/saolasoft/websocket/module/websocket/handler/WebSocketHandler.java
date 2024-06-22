package com.saolasoft.websocket.module.websocket.handler;

import com.saolasoft.websocket.module.websocket.client.WsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

// Create a WebSocket handler to manage incoming connections and forward messages.
@Service("webSocketHandler")
public class WebSocketHandler extends AbstractWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final Map<String, WsClient> clients = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	logger.info("afterConnectionEstablished");
    	
        sessions.put(session.getId(), session);
        // Optionally, connect to other WebSocket servers
        WsClient client = new WsClient(session.getId(), this);
        client.connectToServer("ws://localhost:1234/ws");
        clients.put(session.getId(), client);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("handleTextMessage - message: {}", message.getPayload());
    	
        // Forward message to another WebSocket server
        WsClient client = clients.get(session.getId());
        if (client != null && client.isConnected()) {
            client.sendMessage(message.getPayload());
        }
    }
    
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        logger.info("handleBinaryMessage : {}", message.toString());
    	
        // Forward binary message to another WebSocket server
        WsClient client = clients.get(session.getId());
        if (client != null && client.isConnected()) {
            client.sendMessage(message.getPayload().array());
        }
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	logger.info("afterConnectionClosed");
    	
        sessions.remove(session.getId());
        WsClient client = clients.remove(session.getId());
        if (client != null) {
            client.close();
        }
    }
    
    public void sendMessageToClient(String sessionId, String message) throws Exception {
    	logger.info("sendMessageToClient");
    	
        WebSocketSession session = sessions.get(sessionId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
    
    public void sendBinaryMessageToClient(String sessionId, byte[] message) throws Exception {
    	logger.info("sendBinaryMessageToClient");
    	
        WebSocketSession session = sessions.get(sessionId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new BinaryMessage(message));
        }
    }
}
