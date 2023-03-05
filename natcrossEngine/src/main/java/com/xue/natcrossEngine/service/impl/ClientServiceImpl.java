package com.xue.natcrossEngine.service.impl;

import com.xue.natcrossEngine.service.ClientServiceInterface;
import com.xue.natcrossEngine.serverside.client.ClientServiceThread;
import com.xue.natcrossEngine.serverside.client.config.SimpleClientServiceConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientServiceInterface {

  private static Map<Integer,ClientServiceThread> ClientListenThreadMap = new HashMap<>();
  @Override
  public String openClientListen(Integer port)  throws Exception{
    // 设置并启动客户端服务线程
    SimpleClientServiceConfig config = new SimpleClientServiceConfig(port);
    ClientServiceThread clientServiceThread = new ClientServiceThread(config);
    clientServiceThread.start();
    ClientListenThreadMap.put(port,clientServiceThread);
    return "client listen start.";
  }

  public String closeClientListen(Integer port) throws Exception {
    if(ClientListenThreadMap.containsKey(port)){
      ClientServiceThread clientServiceThread = ClientListenThreadMap.get(port);
      clientServiceThread.cancel();
      return "client listen close...";
    }
    return "client listen not started!";
  }
}
