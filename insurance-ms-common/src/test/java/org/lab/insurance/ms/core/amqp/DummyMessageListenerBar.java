package org.lab.insurance.ms.core.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class DummyMessageListenerBar implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println("OnMessage Foo:");
		System.out.println("  " + message.getBody());
		System.out.println("  " + message);
	}
}
