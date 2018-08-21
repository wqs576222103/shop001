package org.cdlg.converter;




import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateConverter implements Converter<String,Date> {


    @Override
    public Date convert(String source) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             return df.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return null;
    }
}
