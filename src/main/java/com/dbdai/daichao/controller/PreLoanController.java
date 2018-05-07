package com.dbdai.daichao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dbdai.daichao.model.LoanInfo;
import com.dbdai.daichao.model.Merchant;
import com.dbdai.daichao.service.LoanInfoService;
import com.dbdai.daichao.service.MerchantService;
import com.dbdai.daichao.util.Response;

@RestController
@RequestMapping(value = "/preLoan", method = RequestMethod.POST)
public class PreLoanController {

	@Autowired
	private LoanInfoService loanInfoService;
	@Autowired
	private MerchantService merchantService;

	@RequestMapping(value = "/adviseMerchant", method = RequestMethod.POST)
	public Response adviseMerchant(@RequestBody LoanInfo loanInfo) {
		// 存储用户
		loanInfoService.save(loanInfo);
		// 进行推荐逻辑
		List<Merchant> adaptMerchant = merchantService.getAdaptMerchant(loanInfo.getSocre(), loanInfo.getAge(),
				loanInfo.getAmount());
		Response ok = Response.ok();
		ok.setBody(adaptMerchant);
		return ok;
	}

}
