package com.saolasoft.websocket.module.comand.service;

import com.saolasoft.websocket.module.comand.service.dto.Parameter;

import java.util.Map;

public interface CommandService {

    boolean execute(String command);

    boolean execute(Map<Parameter,String> parameters);
}
