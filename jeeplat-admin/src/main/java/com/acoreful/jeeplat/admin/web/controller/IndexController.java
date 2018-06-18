package com.acoreful.jeeplat.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping
@Slf4j
public class IndexController {
	@GetMapping("index")
	public String index(){
		log.info("====================");
		return "index";
	}
}
