package com.learn.boot.ProjectDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learn.boot.processor.DemoProcessor;

@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	DemoProcessor demoProcessor;

	@RequestMapping(value = "/trigger", method = RequestMethod.GET)
	public void trigger() throws Exception {
		System.out.println("Inside trigger");
		demoProcessor.doProcess();
		System.out.println("Outside trigger");

	}

}