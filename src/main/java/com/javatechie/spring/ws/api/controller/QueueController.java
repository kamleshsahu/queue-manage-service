package com.javatechie.spring.ws.api.controller;

import com.javatechie.spring.ws.api.ResponseDTO.ResponseDTO;
import com.javatechie.spring.ws.api.model.Man;
import com.javatechie.spring.ws.api.service.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Controller
public class QueueController {

	@Autowired
	QueueManager queueManager;
	QueueController(QueueManager queueManager){
		this.queueManager=queueManager;
	}

	@MessageMapping("/man.enqueue")
	@SendTo("/topic/inqueue")
	public Man enqueue(@Payload Man man, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", man.getId());
		return queueManager.addToQueue(man);
	}

//	@MessageMapping("/man.enqueue")
//	@SendTo("/topic/inqueue")
//	public Man enqueue(@Payload Man man, SimpMessageHeaderAccessor headerAccessor) {
//		headerAccessor.getSessionAttributes().put("username", man.getId());
//		return queueManager.addToQueue(man);
//	}

	@MessageMapping("/man.dequeue")
	@SendTo("/topic/cancelled")
	public Man deque(@Payload Man man, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", man.getId());
		return queueManager.removeFromQueue(man);
	}

	@MessageMapping("/processFirst")
	@SendTo("/topic/processed")
	public Man processFirst() {
		return queueManager.processFirst();
	}

	@GetMapping("/queue")
	public ResponseDTO<List<Man>> getQueue(){
		ResponseDTO<List<Man>> response = new ResponseDTO<>(HttpStatus.OK,queueManager.getQueue());

		return response;
	}
}
