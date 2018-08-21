package org.cdlg.controller;

import org.cdlg.result.PicUploadResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: wqs
 * @Date: 2018/8/13 0013 10:01
 * @Description: I LOVE IT？
 */
@Controller
@RequestMapping("/pic")
public class UploadController {
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @ResponseBody
    public PicUploadResult upload(@RequestParam MultipartFile uploadFile) throws IOException{
     /*  String mm= this.getClass().getResource("/").getPath();
        System.out.println("----------------------------------------------------");
       System.out.println(mm);
        System.out.println(System.getProperty("user.dir"));*/
        //上传图片的路径
        String path="E://nginx//nginx-1.8.1//uploadimage";
        //服务器路径，方便图片回显
        String webpath="http://image.cdlg.com/";

        //获取后缀
        String filename=uploadFile.getOriginalFilename();
        String end=filename.substring(filename.lastIndexOf("."));

        //新的文件名
        String uuid_name= UUID.randomUUID().toString().replace("-","")+end;
        //File.separator分隔符 //
        File file=new File(path+File.separator+uuid_name);

        //保存到磁盘，
        uploadFile.transferTo(file);

        BufferedImage image= ImageIO.read(file);
        //只是判断图片为空上传失败
       if(image!=null){
           //宽高加上一个""转字符串

return PicUploadResult.buildSuccess(webpath+uuid_name,image.getWidth()+"",image.getHeight()+"");
       }else {
           return PicUploadResult.buildFail("没有找到图片，上传失败");
       }



    }
}
