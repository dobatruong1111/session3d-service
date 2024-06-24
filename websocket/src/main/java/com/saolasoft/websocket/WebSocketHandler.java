package com.saolasoft.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import com.saolasoft.websocket.api.service.session3d.model.Session;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

// Create a WebSocket handler to manage incoming connections and forward messages.
@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final Map<String, WsClient> clients = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private Session3dRepository session3dRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	logger.info("afterConnectionEstablished");
    	
    	// Get session 3d
        URI uri = session.getUri();
        String query = uri.getQuery();
        String session3dId = query.split("&")[0].split("=")[1];
        Session session3d = this.session3dRepository.getById(session3dId);
        // logger.info(String.format("Session3d: id=%s, host=%s, port=%d, cmd=%s", session3d.getId(), session3d.getHost(), session3d.getPort(), session3d.getCmd()));
        
        sessions.put(session.getId(), session);
        // Optionally, connect to other WebSocket servers
        String sessionUrl = String.format("ws://%s:%d/ws", session3d.getHost(), session3d.getPort());
        WsClient client = new WsClient(session.getId(), this, sessionUrl);
        client.connectToServer(sessionUrl);
        clients.put(session.getId(), client);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	logger.info("handleTextMessage - message: " + message.getPayload());
    	
        // Forward message to another WebSocket server
        WsClient client = clients.get(session.getId());
        if (client != null && client.isConnected()) {
            client.sendMessage(message.getPayload());
        }
    }
    
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
    	logger.info("handleBinaryMessage : " + message.toString());
    	
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
