package com.acoreful.jeeplat.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acoreful.jeeplat.commons.api.AcfDemoServiceApi;

@Service
public class SysMngService {
	
	@Autowired
	private AcfDemoServiceApi acfDemoServiceApi;
	
	public void test() {
		acfDemoServiceApi.toString();
	}
}
