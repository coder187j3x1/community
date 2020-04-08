package com.seaxll.community.enums;

/**
 * ClassName: CommentType
 * Description:
 * date: 2020/4/8 8:53
 *
 * @author seaxll
 * @since JDK 1.8
 */
public enum CommentType {
    QUESTION(0),
    COMMENT(1);

    private Integer type;

    public Integer getType() {
        return this.type;
    }

    CommentType(Integer type){
        this.type = type;
    }

    public static boolean isCommentTypeExist(Integer type) {
        boolean isExist = false;
        for (CommentType commentType : CommentType.values()) {
            isExist = commentType.getType().equals(type);
            if (isExist) {
                break;
            }
        }
        return isExist;
    }

}
