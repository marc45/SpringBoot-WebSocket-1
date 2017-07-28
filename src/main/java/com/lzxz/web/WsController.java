package com.lzxz.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.lzxz.domain.WiselyMessage;
import com.lzxz.domain.WiselyResponse;

/**
 * 控制器
 */

@Controller
public class WsController {
	//广播式
	@MessageMapping("/welcome")//通过@MessageMapping映射/welcome地址
	@SendTo("/topic/getResponse")//当服务端有消息时,会对订阅@SendTo中路径的浏览器发送消息
	public WiselyResponse say(WiselyMessage message) throws Exception {
		Thread.sleep(3000);
		return new WiselyResponse("Welcome, " + message.getName() + "!");
	}
	
	//点对点式
	@Autowired
	private SimpMessagingTemplate messagingTemplate;//通过SimpMessagingTemplate向浏览器发送消息

	@MessageMapping("/chat")
	//SpringMVC中直接在参数中获取principal,principal中包含当前用户的信息
	public void handleChat(Principal principal, String msg) { 
		//用于测试，故此处使用硬编码
		if (principal.getName().equals("tangyd")) {
			//messagingTemplate.convertAndSendToUser向用户发送消息
			//参数(接收消息的用户,浏览器订阅的地址,消息体)
			messagingTemplate.convertAndSendToUser("zhaoxz",
					"/queue/notifications", principal.getName() + "-send:" + msg);
		} else {
			messagingTemplate.convertAndSendToUser("tangyd",
					"/queue/notifications", principal.getName() + "-send:" + msg);
		}
	}
}