package com.vk2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;


public class MyVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture)
	{
		System.out.println("MyVerticle started");
	}
	
	@Override
	public void stop(Future stopFuture) throws Exception
	{
		System.out.println("MyVerticle stopped");
	}
	
	public static void main(String[] args) {
		VertxOptions options = new VertxOptions().setWorkerPoolSize(10);
		Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle("com.vk2.MyVerticle");
	}

}
