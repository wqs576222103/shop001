package org.cdlg.exception;

public class CustomException extends  Exception {
    private  String message;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super();
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
