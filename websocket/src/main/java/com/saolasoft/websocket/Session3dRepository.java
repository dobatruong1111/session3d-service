package com.saolasoft.websocket;

import org.springframework.stereotype.Repository;
import com.saolasoft.websocket.api.service.session3d.model.Session;
import java.util.HashMap;
import java.util.Map;

@Repository
public class Session3dRepository {
	
	private Map<String, Session> sessions = new HashMap<>();

    public Session saveSession(Session session) {
        sessions.put(session.getId(), session);
        return session;
    }

    public Session getById(String sessionId) {
        return sessions.get(sessionId);
    }
}
