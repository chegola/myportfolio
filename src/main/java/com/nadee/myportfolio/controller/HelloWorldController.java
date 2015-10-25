package com.nadee.myportfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloWorldController {

		@RequestMapping(value="/helloWorld", method = RequestMethod.GET)
		public String hellox(ModelMap model) {

			model.addAttribute("name", "JCG Hello World!");
			return "helloWorld";

		}
		
	}