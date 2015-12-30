package com.mocredit.integral.service.impl;

import com.mocredit.integral.entity.Terminal;
import com.mocredit.integral.persistence.TerminalMapper;
import com.mocredit.integral.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ytq on 2015/11/17.
 */
@Service
public class TerminalServiceImpl implements TerminalService {
    @Autowired
    private TerminalMapper terminalMapper;

    @Override
    public Terminal getTerminalByEnCodeAndActivityId(String enCode, String activityId) {
        return terminalMapper.getTerminalByEnCodeAndActivityId(enCode, activityId);
    }
}
