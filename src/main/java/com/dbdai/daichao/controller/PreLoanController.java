package com.dbdai.daichao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
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

	public static void main(String[] args) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("socre", 600);
		jsonMap.put("age", 15);
		jsonMap.put("amount", 600);
		jsonMap.put("phone", "22111231");
		System.out.println(JSONObject.toJSON(jsonMap));
	}
}
