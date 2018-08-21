package org.cdlg.converter;


import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Auther: wqs
 * @Date: 2018/8/20 0020 09:31
 * @Description: I LOVE IT？
 */

public class CallbackMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    // 做jsonp的支持的标识，在请求参数中加该参数
    private String callbackName;

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
// 从threadLocal中获取当前的Request对象
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String callbackParam=request.getParameter(callbackName);
        if (StringUtil.isEmpty(callbackParam)){
            super.writeInternal(object,outputMessage);
        }else {
            JsonEncoding jsonEncoding=getJsonEncoding(outputMessage.getHeaders().getContentType());
            try {
                //对返回值进行包裹封装
                String result=callbackParam+"("+super.getObjectMapper().writeValueAsString(object)+")";

                IOUtils.write(result,outputMessage.getBody(),jsonEncoding.getJavaName());

            }catch (JsonProcessingException e){
                throw new HttpMessageNotWritableException("Could not write JSON: "+e.getMessage(),e);
            }
        }

    }

    public String getCallbackName() {
        return callbackName;
    }

    public void setCallbackName(String callbackName) {
        this.callbackName = callbackName;
    }
}
