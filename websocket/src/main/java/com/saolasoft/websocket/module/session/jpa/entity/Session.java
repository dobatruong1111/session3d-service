package com.saolasoft.websocket.module.session.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.saolasoft.base.persistence.model.BaseSerialIDEntry;

@Entity
@Table(name = "session")
@Getter @Setter @NoArgsConstructor
public class Session extends BaseSerialIDEntry {

    @Column(name = "session")
    private String sessionID;

    // Các trường khác cần lưu
}
