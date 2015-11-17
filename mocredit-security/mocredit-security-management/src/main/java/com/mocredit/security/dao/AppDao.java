package com.mocredit.security.dao;

import com.mocredit.security.entity.App;

import java.util.List;

/**
 * <p>User:
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface AppDao {

    public App createApp(App app);

    public App updateApp(App app);

    public void deleteApp(Long appId);

    public App findOne(Long appId);

    public App findOneByUserId(Long userId);

    public List<App> findAll();

    Long findAppIdByAppKey(String appKey);

    List<App> findAppByList(Integer page, Integer pageSize);
}
