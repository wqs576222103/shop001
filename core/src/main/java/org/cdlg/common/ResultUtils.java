package org.cdlg.common;

//防止警告未经检查或者不安全的操作
@SuppressWarnings("unchecked")
public class ResultUtils {


    public  static  Result buildFail(Integer code,String message){
        Result result=new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setStatus("fail");
        return  result;
    }
    public  static  Result buildSuccess(){
        Result result=new Result();
        result.setStatus("success");
        return  result;
    }
    public  static  Result buildSuccess(Object object){
        Result result=new Result();
        result.setStatus("success");
        result.setData(object);
        return  result;
    }
}
