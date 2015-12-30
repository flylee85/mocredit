/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.mocredit.security.remote;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.HashMap;

/**
 * <p>User:
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 */
public interface RemoteServiceInterface {

    public Session getSession(String appKey, Serializable sessionId);

    Serializable createSession(Session session);

    public void updateSession(String appKey, Session session);

    public void deleteSession(String appKey, Session session);

    public PermissionContext getPermissions(String appKey, String username);
}
