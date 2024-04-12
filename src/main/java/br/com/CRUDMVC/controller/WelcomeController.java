package br.com.CRUDMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String Welcome() {
		return "redirect:/usuario/todos";
	}
	
	@RequestMapping(value="/Teste", method = RequestMethod.GET)
	public ModelAndView teste() {
		ModelAndView view = new ModelAndView("welcome");
		view.addObject("teste", "Vim do model and view galeru");
		return view;
	}
	
}
