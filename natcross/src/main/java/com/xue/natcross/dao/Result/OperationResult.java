package com.xue.natcross.dao.Result;

import lombok.Data;

import java.io.Serializable;

@Data
public class OperationResult<T>  implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer Code;

  private String Msg;
  private T Data;

  public OperationResult(int code,T data){
    this.Code = code;
    this.Data = data;
  }

  public OperationResult(int code,String msg){
    this.Code = code;
    this.Msg = msg;
  }
  public OperationResult(int code,String msg,T data){
    this.Code = code;
    this.Msg = msg;
    this.Data = data;
  }

}
