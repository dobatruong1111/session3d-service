package com.saolasoft.websocket.module.session.jpa;

import com.saolasoft.websocket.module.session.jpa.entity.Session;
import org.springframework.stereotype.Repository;
import vn.saolasoft.base.persistence.repository.BaseRepository;

@Repository("sessionRepository")
public interface SessionRepository extends BaseRepository<Session, Long> {
}