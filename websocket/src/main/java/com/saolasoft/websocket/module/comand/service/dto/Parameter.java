package com.saolasoft.websocket.module.comand.service.dto;

import lombok.Getter;

@Getter
public enum Parameter {

    HOST("host"),
    PORT("port"),
    SECRET("secret"),
    STUDY_INSTANCE_UID("studyiuid"),
    SERIES_INSTANCE_UID("series_instanse_uid"),
    SESSION("session"),
    ;
    private final String value;

    Parameter(String value) {
        this.value = value;
    }
}
