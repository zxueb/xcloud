package com.xue.natcross.controller;

import com.xue.natcross.dao.Result.NatMapping;
import com.xue.natcross.dao.Result.OperationResult;
import com.xue.natcross.service.ManagerServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/manager")
public class ManagerController {

  @Autowired
  private ManagerServiceInterface managerService;

  @RequestMapping("/openClientSideListen")
  public OperationResult<String> openClientSideListen(){
    try{
      managerService.openClientSideListen();
    }catch (Exception e){
        log.error("内网监听端口开启失败！",e);
        return new OperationResult<>(417,"开启失败！"+e.getMessage());
    }

    return new OperationResult<>(200,"开启成功！");
  }

  @RequestMapping("/closeClientSideListen")
  public OperationResult<String> closeClientSideListen(){
    try{
      managerService.closeClientSideListen();
    }catch (Exception e){
      log.error("内网监听端口关闭失败！",e);
      return new OperationResult<>(417,"关闭失败！"+e.getMessage());
    }

    return new OperationResult<>(200,"关闭成功！");
  }

  @PostMapping("/createMapping")
  public OperationResult<String> createMapping(@RequestBody NatMapping natMapping){
    try{
      String msg = managerService.createMapping(natMapping);
      return new OperationResult<>(200,msg);
    }catch (Exception ex){
      log.error("create mapping error",ex);
      return new OperationResult<>(417,"创建失败");
    }
  }

  @RequestMapping("/getRandomPort")
  public OperationResult<Integer> getRandomPort(){
    try {
      int port = managerService.getRandomPort();
      return new OperationResult<>(200,port);
    } catch (IOException e) {
      log.error("获取端口号失败",e);
    }
    return new OperationResult<>(417,-1);
  }

  @RequestMapping("/hello")
  public String hello(){
    return "hello world";
  }

  @PostMapping("/deleteMapping")
  public String deleteMapping(){
    return "操作成功";
  }

  @PostMapping("/modifyMapping")
  public String modifyMapping(){
    return "操作成功";
  }

  @PostMapping("/closeMapping")
  public OperationResult<String> closeMapping(@RequestBody NatMapping natMapping){
    try {
      String msg = managerService.closeMapping(natMapping);
      return new OperationResult<>(200,msg);
    } catch (Exception e) {
      log.error("create mapping error",e);
      return new OperationResult<>(417,"关闭失败");
    }
  }


}
