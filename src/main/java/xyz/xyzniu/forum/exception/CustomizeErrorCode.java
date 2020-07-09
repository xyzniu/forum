package xyz.xyzniu.forum.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("问题未找到"),
    USER_NOT_FOUND("用户未登陆");
    
    private String message;
    
    CustomizeErrorCode(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
