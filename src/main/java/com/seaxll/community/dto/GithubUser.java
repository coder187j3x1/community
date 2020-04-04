package com.seaxll.community.dto;

import lombok.Data;

/**
 * ClassName: GithubUser
 * Description:
 * date: 2020/4/2 21:15
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Data
public class GithubUser {
    private String name;
    private long id;
    private String bio;
}
