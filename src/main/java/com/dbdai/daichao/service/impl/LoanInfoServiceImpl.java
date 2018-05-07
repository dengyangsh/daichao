package com.dbdai.daichao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdai.daichao.dao.LoanInfoDao;
import com.dbdai.daichao.service.LoanInfoService;



@Service(value = "loanInfoService")
public class LoanInfoServiceImpl implements LoanInfoService {

	@Autowired
	private LoanInfoDao loanInfoDao;
	
	
}
