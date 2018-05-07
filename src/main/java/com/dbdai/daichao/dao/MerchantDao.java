package com.dbdai.daichao.dao;

import java.math.BigDecimal;
import java.util.List;

import com.dbdai.daichao.model.Merchant;

/**
 * 
 * 
 * @author bixia-generator
 * @email bixia@hebao.im
 * @date 2018-05-07 20:29:04
 */
public interface MerchantDao extends BaseDAO<Merchant> {

	List<Merchant> getAdaptMerchant(Integer score, Integer age, BigDecimal amount);
	
}
