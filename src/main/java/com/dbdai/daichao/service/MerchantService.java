package com.dbdai.daichao.service;

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
public interface MerchantService {

	/**
	 * 獲取最合適的商家
	 * @param score
	 * @param age
	 * @param amount
	 * @return
	 */
	public List<Merchant> getAdaptMerchant(Integer score, Integer age, BigDecimal amount);
}
