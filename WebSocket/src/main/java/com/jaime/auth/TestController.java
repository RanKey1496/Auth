package com.jaime.auth;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@MessageMapping("/chat/{topic}")
	@SendTo("/topic/messages/")
	public OutputMessage send(@DestinationVariable("topic") String topic, Message message) throws Exception {
		System.out.println(topic);
		return new OutputMessage(message.getFrom(), message.getText(), topic);
	}
	
}
