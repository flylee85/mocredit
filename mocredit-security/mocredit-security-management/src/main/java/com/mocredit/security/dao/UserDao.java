package com.mocredit.security.dao;

import com.mocredit.security.entity.User;

import java.util.List;

/**
 * <p>User:
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserDao {

    public User createUser(User user);

    public User updateUser(User user);

    public void deleteUser(Long userId);

    User findOne(Long userId);

    List<User> findAll();

    User findByUsername(String username);

    List<User> findUserByList(Integer page, Integer pageSize);

    int findUserCount();

}
