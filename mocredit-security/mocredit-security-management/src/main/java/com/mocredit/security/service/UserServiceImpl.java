package com.mocredit.security.service;

import com.mocredit.security.dao.UserDao;
import com.mocredit.security.entity.User;
import com.mocredit.security.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>User:
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private RoleService roleService;

    /**
     * 创建用户
     *
     * @param user
     */
    public User createUser(User user) {
        //加密密码
        user.setMd5Pwd(MD5Util.MD5(user.getPassword()));
        passwordHelper.encryptPassword(user);
        return userDao.createUser(user);
    }

    @Override
    public User updateUser(User user, User oUser) {
        if (!oUser.getPassword().equals(user.getPassword())) {
            user.setMd5Pwd(MD5Util.MD5(user.getPassword()));
            passwordHelper.encryptPassword(user);
        } else {
            user.setMd5Pwd(oUser.getMd5Pwd());
        }
        return userDao.updateUser(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userDao.deleteUser(userId);
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword) {
        User user = userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

    @Override
    public User findOne(Long userId) {
        return userDao.findOne(userId);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> findUserByList(Integer page, Integer pageSize) {
        return userDao.findUserByList(page, pageSize);
    }

    public int findUserCount() {
        return userDao.findUserCount();
    }
}
