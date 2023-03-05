package com.xue.natcrossEngine.service.impl;

import com.xue.natcrossEngine.serverside.listen.ServerListenThread;
import com.xue.natcrossEngine.service.ServeServiceInterface;
import com.xue.natcrossEngine.serverside.listen.ListenServerControl;
import com.xue.natcrossEngine.serverside.listen.config.SimpleListenServerConfig;
import org.springframework.stereotype.Service;

@Service
public class ServeServiceImpl implements ServeServiceInterface {
  @Override
  public String openServeListen(Integer port) throws Exception {
    // 设置并启动一个穿透端口,只是占用端口
    SimpleListenServerConfig listengConfig = new SimpleListenServerConfig(port);
    //listengConfig.setCreateServerSocket(createServerSocket);
    ListenServerControl.createNewListenServer(listengConfig);
    return "serve listen start.";
  }

  @Override
  public String closeServeListen(Integer port) throws Exception {
    ServerListenThread serverListenThread = ListenServerControl.get(port);
    serverListenThread.cancel();
    return "serve listen cloes success!";
  }

}
