package com.seaxll.community.exception;

import com.seaxll.community.enums.ErrorCode;

/**
 * ClassName: CommunityException
 * Description:
 * date: 2020/4/6 18:49
 *
 * @author seaxll
 * @since JDK 1.8
 */
public class CommunityException extends RuntimeException {
    ErrorCode errorCode;

    public CommunityException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return errorCode.getMessage();
    }

    public Integer getCode() {
        return errorCode.getCode();
    }
}
