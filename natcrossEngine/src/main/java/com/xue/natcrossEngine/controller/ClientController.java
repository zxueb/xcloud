package com.xue.natcrossEngine.controller;

import com.xue.natcrossEngine.service.ClientServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {

  @Autowired
  private ClientServiceInterface clientService;

  @RequestMapping("/openListen")
  public String openClientListen(@RequestBody Integer port){
    try {
      clientService.openClientListen(port);
    } catch (Exception e) {
      log.error("open client listen failed.",e);
      return "client listen start failed";
    }
    return "client listen start...";
  }

  @RequestMapping("/closeListen")
  public String closeClientListen(@RequestBody Integer port){
    try {
      return clientService.closeClientListen(port);
    } catch (Exception e) {
      log.error("close client listen failed.",e);
    }
    return "client listen close failed";
  }
}
