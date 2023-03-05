package com.xue.natcross.service.impl;

import com.xue.natcross.dao.Result.NatMapping;
import com.xue.natcross.proxy.NatcrossEngineService;
import com.xue.natcross.service.ManagerServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;

@Service
@Slf4j
public class ManagerServiceImpl implements ManagerServiceInterface {

  @Autowired
  private NatcrossEngineService natcrossEngineService;

  @Override
  public String createMapping(NatMapping natMapping) throws Exception{
    return natcrossEngineService.openServeListen(natMapping.getOutlistenPort());
  }

  @Override
  public String deleteMapping() {
    return null;
  }

  @Override
  public String modifyMapping() {
    return null;
  }

  @Override
  public Integer getRandomPort() throws IOException {
    ServerSocket serverSocket = new ServerSocket(0);
    int localPort = serverSocket.getLocalPort();
    //这里一定要close(),不然这个端口无法被其他程序使用
    serverSocket.close();
    return localPort;
  }

  @Override
  public String openClientSideListen() {
    Integer clientListPort = 10010;
    return natcrossEngineService.openClientListen(clientListPort);
  }

  @Override
  public String closeClientSideListen() {
    Integer clientListPort = 10010;
    return natcrossEngineService.closeClientListen(clientListPort);
  }

  @Override
  public String closeMapping(NatMapping natMapping) throws Exception {
    return natcrossEngineService.closeServeListen(natMapping.getOutlistenPort());
  }

  @Override
  public String openMapping(NatMapping natMapping) throws Exception {
    return null;
  }


}
