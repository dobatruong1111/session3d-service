package com.saolasoft.websocket.module.session.service;

import com.saolasoft.websocket.module.session.jpa.SessionRepository;
import com.saolasoft.websocket.module.session.service.dto.SessionDTOCreate;
import com.saolasoft.websocket.module.session.service.dto.SessionDTOGet;
import com.saolasoft.websocket.module.comand.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SessionRepository sessionRepository;

    private final CommandService commandService;

    public SessionServiceImpl(SessionRepository sessionRepository,
                              CommandService commandService) {
        this.sessionRepository = sessionRepository;
        this.commandService = commandService;
    }

    @Override
    public SessionDTOGet createSession(SessionDTOCreate session) {
        // TODO
        // 1. Tạo Session và lưu vào bảng session
        // 2. Start tiến trình 3D server (commandService)
        // 3. Check bước 2 trả về status code nào đó để return giá trị về
        return null;
    }

    @Override
    public SessionDTOGet getSession(String sessionId) {
        // 1. Query trong db = sessionID
        // 2. Check điều kiện để return

        return null;
    }

    @Override
    public void deleteSession(String sessionId) {
        // Logic xóa
    }
}
