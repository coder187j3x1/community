package com.seaxll.community.exception;

/**
 * ClassName: ErrorCode
 * Description:
 * date: 2020/4/6 18:42
 *
 * @author seaxll
 * @since JDK 1.8
 */
public enum ErrorCode {
    QUESTION_NOT_FOUND(1, "您访问的问题不存在"),
    USER_NOT_FOUND(2, "该用户不存在"),
    PLEASE_LOGIN(3, "请登录后操作"),
    COMMENT_IS_EMPTY(4, "评论为空");
    
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
