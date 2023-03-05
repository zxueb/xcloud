package com.xue.natcrossEngine.service;

public interface ServeServiceInterface {

  String openServeListen(Integer port) throws Exception;

  String closeServeListen(Integer port) throws Exception;
}
