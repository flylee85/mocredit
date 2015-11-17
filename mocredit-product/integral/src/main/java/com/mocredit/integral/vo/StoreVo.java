package com.mocredit.integral.vo;

import com.mocredit.integral.entity.Store;
import com.mocredit.integral.entity.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytq on 2015/11/12.
 */
public class StoreVo extends Store {
    private List<Terminal> terminals = new ArrayList<Terminal>();

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public void setTerminals(List<Terminal> terminals) {
        this.terminals = terminals;
    }
}
