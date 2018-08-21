package org.cdlg.exception;

import org.cdlg.common.ResponseUtils;
import org.cdlg.common.Result;
import org.cdlg.common.ResultUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class CustomHandlerException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //是否为可预期异常
        String msg;
          if(ex instanceof  CustomException){
              msg=ex.getMessage();
          }else{//不可预期异常
              ex.printStackTrace();
              msg="系统开小差了";
          }
        //判断处理器上面有没有ResponseBody注解或者处理器返回的类型ResponseEntity
      //获取处理器
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
//查看上面是否有这个注释
        ResponseBody responseBody= AnnotationUtils.findAnnotation(method, ResponseBody.class);
        ModelAndView modelAndView=new ModelAndView();
        //有ResponseBody就返回json
        if (responseBody!=null|| ResponseEntity.class.getName().equals(method.getReturnType().getName())){
            //返回json数据
            Result result= ResultUtils.buildFail(500,msg);
            ResponseUtils.ResponseToObject(response,result);
            return modelAndView;

        }
        modelAndView.addObject("msg",msg);
        modelAndView.setViewName("error");
        return  modelAndView;




    }
}
