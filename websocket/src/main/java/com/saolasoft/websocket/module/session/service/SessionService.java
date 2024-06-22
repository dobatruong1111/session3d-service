package com.saolasoft.websocket.module.session.service;

import com.saolasoft.websocket.module.session.service.dto.SessionDTOCreate;
import com.saolasoft.websocket.module.session.service.dto.SessionDTOGet;

public interface SessionService {
    SessionDTOGet createSession(SessionDTOCreate session);

    SessionDTOGet getSession(String sessionId);

    void deleteSession(String sessionId);
}
