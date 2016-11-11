package com.nkttk.core.components.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.UUID;

/**
 * Created by cryptobat on 11/1/2016.
 */
public class LambdaInstance<I,O> {
  private String  url;
  private RequestHandler<I,O> handler;
  private String name;
  private LambdaContext context;

  public LambdaInstance(String name, RequestHandler<I,O> handler){
    this.url = UUID.randomUUID().toString();
    this.handler = handler;
    this.context = new LambdaContext(name);
    this.name = name;
  }

  public O execute(I input){
    return handler.handleRequest(input, context);
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }
}
