package com.mocredit.security.service;

import com.mocredit.security.entity.App;

import java.util.List;

/**
 * <p>User:
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface AppService {


    public App createApp(App app);

    public App updateApp(App app);

    public void deleteApp(Long appId);

    public App findOne(Long appId);

    public List<App> findAll();

    /**
     * 根据appKey查找AppId
     *
     * @param appKey
     * @return
     */
    public Long findAppIdByAppKey(String appKey);

    public App findOneByUserId(Long userId);

    List<App> findAppByList(Integer page, Integer pageSize);
}
