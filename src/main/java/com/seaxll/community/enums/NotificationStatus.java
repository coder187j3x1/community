package com.seaxll.community.enums;

/**
 * ClassName: NotificationStatus
 * Description:
 * date: 2020/4/12 8:57
 *
 * @author seaxll
 * @since JDK 1.8
 */
public enum NotificationStatus {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatus(int status) {
        this.status = status;
    }
}
