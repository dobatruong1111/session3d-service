package com.saolasoft.websocket.module.session.rest;

import com.saolasoft.websocket.module.session.service.SessionService;
import com.saolasoft.websocket.module.session.service.dto.SessionDTOCreate;
import com.saolasoft.websocket.module.session.service.dto.SessionDTOGet;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ws/rest/v1/session") // Compatible with viewer-BE
public class SessionAPI {

    private final Logger logger = LoggerFactory.getLogger(SessionAPI.class);

    private final SessionService sessionService;

    public SessionAPI(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<SessionDTOGet> getWsLink(@PathVariable String sessionID,
                                                   @Validated @RequestBody SessionDTOCreate session) {
        try {
            SessionDTOGet sessionDTOGet = sessionService.createSession(session);
            return ResponseEntity.ok(sessionDTOGet);
        } catch (Exception ex) {
            logger.error("[SessionID-{}] Create session error - {}", sessionID, ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{sessionID}")
    public ResponseEntity<SessionDTOGet> getSession(@PathVariable String sessionID) {
        try {
            SessionDTOGet sessionDTOGet = sessionService.getSession(sessionID);
            return ResponseEntity.ok(sessionDTOGet);
        } catch (ObjectNotFoundException ex) {
            logger.error("[SessionID-{}] Object not found", sessionID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("[SessionID-{}] Get session error - {}", sessionID, ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{sessionID}")
    public ResponseEntity<SessionDTOGet> deleteSession(@PathVariable String sessionID) {
        try {
            sessionService.deleteSession(sessionID);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException ex) {
            logger.error("[SessionID-{}] Object not found", sessionID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("[SessionID-{}] Delete session error - {}", sessionID, ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
