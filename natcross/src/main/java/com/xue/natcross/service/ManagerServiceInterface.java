package com.xue.natcross.service;

import com.xue.natcross.dao.Result.NatMapping;
import com.xue.natcross.dao.Result.OperationResult;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

public interface ManagerServiceInterface {

  String createMapping(NatMapping natMapping) throws Exception;

  String deleteMapping();

  String modifyMapping();

  Integer getRandomPort() throws IOException;

  String openClientSideListen();

  String closeClientSideListen();

  String closeMapping(NatMapping natMapping) throws Exception;

  String openMapping(NatMapping natMapping) throws Exception;
}
