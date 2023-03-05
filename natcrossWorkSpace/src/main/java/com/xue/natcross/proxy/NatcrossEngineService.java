package com.xue.natcross.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "natcrossEngine")
public interface NatcrossEngineService {

  @RequestMapping("/client/openListen")
  String openClientListen(@RequestBody Integer port);

  @RequestMapping("/client/closeListen")
  String closeClientListen(@RequestBody Integer port);

  @RequestMapping("/serve/openListen")
  String openServeListen(@RequestBody Integer port);

  @RequestMapping("/serve/closeListen")
  String closeServeListen(@RequestBody Integer port);
}
