package com.dbdai.daichao.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dbdai.daichao.dao.BaseDAOImpl;
import com.dbdai.daichao.dao.LoanInfoDao;
import com.dbdai.daichao.model.LoanInfo;

/**
 * 
 * 
 * @author bixia-generator
 * @email bixia@hebao.im
 * @date 2018-05-07 20:29:04
 */
@Repository
public class LoanInfoDaoImpl extends BaseDAOImpl<LoanInfo> implements LoanInfoDao {
	@Autowired
	public void setJdbc(NamedParameterJdbcTemplate jdbcDaiChao) {
		super.setJdbc(jdbcDaiChao);
	}
}
