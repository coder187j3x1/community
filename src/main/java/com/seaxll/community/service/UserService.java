package com.seaxll.community.service;

import com.seaxll.community.dto.GithubUser;
import com.seaxll.community.mapper.UserMapper;
import com.seaxll.community.model.User;
import com.seaxll.community.provider.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserService
 * Description:
 * date: 2020/4/6 10:56
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserProvider userProvider;

    /**
     * 验证 user 在数据库中是否存在 并判断用户信息是否更改
     *
     * @param githubUser 用 GitHub 中的信息封装的 GitHubUser
     * @param token user 的 token
     * @return
     */
    public User verifyUser(GithubUser githubUser, String token) {
        User user = userMapper.findUserByAccountId(githubUser.getId());
        User newUser = new User(githubUser.getName(), String.valueOf(githubUser.getId()), token, githubUser.getAvatarUrl());
        if (user == null) { // 数据库中没有这个 user
            user = newUser;
            userMapper.insertUser(user);
        } else if (!userProvider.isUserEquals(user, newUser)){  // user 信息已经更改
            user = newUser;
            userMapper.updateUser(user);
        }
        return user;
    }
}
