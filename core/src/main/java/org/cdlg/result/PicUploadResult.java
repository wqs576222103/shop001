package org.cdlg.result;

/**
 * @Auther: wqs
 * @Date: 2018/8/13 0013 09:58
 * @Description: I LOVE IT？
 */
public class PicUploadResult {
    private  Integer error;
    private  String url;
    private  String message;
    private  String width;
    private  String height;

    public static PicUploadResult buildSuccess(String url,String width,String height){
        PicUploadResult picUploadResult=new PicUploadResult();
        picUploadResult.setError(0);
        picUploadResult.setUrl(url);
        picUploadResult.setWidth(width);
        picUploadResult.setHeight(height);
        return picUploadResult;
    }

    public static PicUploadResult buildFail(String message){
        PicUploadResult picUploadResult=new PicUploadResult();
        picUploadResult.setError(1);
        picUploadResult.setMessage(message);
        return picUploadResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
