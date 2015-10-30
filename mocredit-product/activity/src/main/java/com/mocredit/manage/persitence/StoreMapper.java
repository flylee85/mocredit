package com.mocredit.manage.persitence;

import java.util.List;

import com.mocredit.manage.model.Store;

public interface StoreMapper {
    List<Store> selectAll(Store store);
}