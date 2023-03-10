package com.xue.engine.service;

public interface ServeServiceInterface {

  String openServeListen(Integer port) throws Exception;

  String closeServeListen(Integer port) throws Exception;
}
