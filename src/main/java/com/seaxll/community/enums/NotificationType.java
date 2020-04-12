package com.seaxll.community.enums;

/**
 * ClassName: NotificationType
 * Description:
 * date: 2020/4/12 8:15
 *
 * @author seaxll
 * @since JDK 1.8
 */
public enum NotificationType {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论");
    private int type;
    private String name;


    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationType(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationType notificationTypeEnum : NotificationType.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
