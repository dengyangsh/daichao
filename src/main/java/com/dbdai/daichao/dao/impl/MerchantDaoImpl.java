package com.dbdai.daichao.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dbdai.daichao.dao.BaseDAOImpl;
import com.dbdai.daichao.dao.MerchantDao;
import com.dbdai.daichao.model.Merchant;
import com.hebao.bixia.common.page.QueryCriteria;

/**
 * 
 * 
 * @author bixia-generator
 * @email bixia@hebao.im
 * @date 2018-05-07 20:29:04
 */
@Repository
public class MerchantDaoImpl extends BaseDAOImpl<Merchant> implements MerchantDao {
	@Autowired
	public void setJdbc(NamedParameterJdbcTemplate jdbcDaiChao) {
		super.setJdbc(jdbcDaiChao);
	}
	@Override
	public List<Merchant> getAdaptMerchant(Integer score, Integer age, BigDecimal amount) {
		QueryCriteria criteria = new QueryCriteria();
		criteria.andColumnGreaterThanOrEqualTo(Merchant.ZHI_MA_SCORE, score);
		criteria.andColumnGreaterThanOrEqualTo(Merchant.MIN_AGE, age);
		criteria.andColumnLessThanOrEqualTo(Merchant.MAX_AGE, age);
		criteria.andColumnGreaterThanOrEqualTo(Merchant.MIN_AMOUNT, amount);
		List<Merchant> data = getData(criteria.getWhereSql(), criteria.getWhereValue());
		return data;
	}

}
