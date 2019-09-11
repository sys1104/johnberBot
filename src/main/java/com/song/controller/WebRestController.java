package com.song.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebRestController {

	@RequestMapping("/main")
	public ModelAndView gotoMain() {
		ModelAndView mav = new ModelAndView("Main");
		return mav;
	}
	
}