package com.mocredit.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.manage.model.Supplier;
import com.mocredit.manage.persitence.SupplierMapper;
import com.mocredit.manage.service.SupplierService;

/**
 * 
 * @author liaoy
 * @date 2015年11月18日
 */
@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	private SupplierMapper supplierMapper;

	@Override
	public List<Supplier> getAll() {
		return supplierMapper.selectAll();
	}
}
