package com.xue.engine.service;


public interface ClientServiceInterface {

  String openClientListen(Integer port) throws Exception;

  String closeClientListen(Integer port) throws Exception;
}
