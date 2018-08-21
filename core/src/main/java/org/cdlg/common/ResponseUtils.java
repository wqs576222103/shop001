package org.cdlg.common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {
     public  static void ResponseToJson(HttpServletResponse response,String json){
      response.setContentType("application/json;charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      try{
        response.getWriter().write(json);
      }catch (IOException e){
          e.printStackTrace();
      }

     }
    public  static void ResponseToObject(HttpServletResponse response, Object obj){
         String json=JsonUtils.objectToJson(obj);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
          response.getWriter().write(json);
        }catch (IOException e){
        e.printStackTrace();
        }



    }

}
