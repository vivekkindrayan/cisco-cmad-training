package com.vk2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class PublisherVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture)
	{
		vertx.eventBus().publish("Channel1", "message 1");
		System.out.println("Published message 1");
		System.out.println("PublisherVerticle started");
		startFuture.complete();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void stop(Future stopFuture) throws Exception
	{
		System.out.println("PublisherVerticle stopped");
		stopFuture.complete();
	}

}
