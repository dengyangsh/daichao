package com.dbdai.daichao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dbdai.daichao.util.Response;

@RestController
@RequestMapping(value = "/preLoan", method = RequestMethod.POST)
public class PreLoanController {

	@RequestMapping(value = "/preLoan", method = RequestMethod.POST)
	public Response adviseMerchant() {
		return Response.ok();
	}

}
