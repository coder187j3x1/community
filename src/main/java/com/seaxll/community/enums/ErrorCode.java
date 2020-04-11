package com.seaxll.community.enums;

/**
 * ClassName: ErrorCode
 * Description:
 * date: 2020/4/6 18:42
 *
 * @author seaxll
 * @since JDK 1.8
 */
public enum ErrorCode {
    QUESTION_NOT_FOUND(2001, "您访问的问题不存在"),
    USER_NOT_FOUND(2002, "该用户不存在"),
    PLEASE_LOGIN(2003, "请登录后操作"),
    COMMENT_IS_EMPTY(2004, "评论为空"),
    COMMENT_TYPE_NOT_FOUND(2005, "评论类型不存在"),
    COMMENT_NOT_FOUND(2006, "评论不存在"),
    ;
    
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
