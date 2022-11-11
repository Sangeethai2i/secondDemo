package com;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.*;

public class vehicle extends AbstactController {
	
	protected ModelAndView insert() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("arun", "sdsdsdsd");
		return mv;
	}

}
