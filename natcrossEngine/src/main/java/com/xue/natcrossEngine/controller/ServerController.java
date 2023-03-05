package com.xue.natcrossEngine.controller;

import com.xue.natcrossEngine.service.ClientServiceInterface;
import com.xue.natcrossEngine.service.ServeServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serve")
@Slf4j
public class ServerController {

  @Autowired
  private ServeServiceInterface serveService;

  @RequestMapping("/openListen")
  public String openServeListen(@RequestBody Integer port){
    try {
      serveService.openServeListen(port);
    } catch (Exception e) {
      log.error("open serve listen failed.",e);
    }
    return "serve listen start...";
  }

  @RequestMapping("/closeListen")
  public String closeServeListen(@RequestBody Integer port){
    try {
      serveService.closeServeListen(port);
    } catch (Exception e) {
      log.error("close serve listen failed.",e);
    }
    return "serve listen close success...";
  }
}
