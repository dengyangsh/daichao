package com.dbdai.daichao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdai.daichao.dao.MerchantDao;
import com.dbdai.daichao.service.MerchantService;



@Service(value = "merchantService")
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantDao merchantDao;
	
	
}
