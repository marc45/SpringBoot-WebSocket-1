package com.lzxz;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
//开启使用STOMP协议来传输基于代理(message broker)的消息，这时控制器支持使用@MessageMapping
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {//注册STOMP协议的节点(endpoint),并映射指定的URL
        registry.addEndpoint("/endpointWisely").withSockJS(); //注册一个STOMP的endpoint(/endpointWisely),并使用SockJS协议
        registry.addEndpoint("/endpointChat").withSockJS();//注册一个STOMP的endpoint(/endpointChat),并使用SockJS协议
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {//配置代理消息(message broker)
    	//广播式且要配置一个/topic代理(点对点式不需要配置,但是要增加一个/queue消息代理)
        registry.enableSimpleBroker("/queue","/topic"); 
    }

}
