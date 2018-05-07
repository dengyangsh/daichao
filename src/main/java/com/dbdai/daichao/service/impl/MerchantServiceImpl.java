package com.dbdai.daichao.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdai.daichao.dao.MerchantDao;
import com.dbdai.daichao.model.Merchant;
import com.dbdai.daichao.service.MerchantService;

@Service(value = "merchantService")
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantDao merchantDao;

	@Override
	public List<Merchant> getAdaptMerchant(Integer score, Integer age, BigDecimal amount) {
		List<Merchant> adaptMerchant = null;
		adaptMerchant = merchantDao.getAdaptMerchant(score, age, amount);
		if (adaptMerchant == null) {
			// 没有查询到完全匹配的商户,匹配用户可以借的
			adaptMerchant = merchantDao.getAdaptMerchant(score, age, null);
			if (adaptMerchant == null) {
				//
				adaptMerchant = merchantDao.getAdaptMerchant(score, null, null);
				if (adaptMerchant == null) {
					adaptMerchant = merchantDao.getAdaptMerchant(null, null, null);
				}
			}
		}
		return adaptMerchant;

	}

}
