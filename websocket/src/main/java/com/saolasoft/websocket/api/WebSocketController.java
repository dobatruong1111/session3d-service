package com.saolasoft.websocket.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	
    @GetMapping("/hello")
    public String hello() {
		return String.format("Hello");
    }
}
