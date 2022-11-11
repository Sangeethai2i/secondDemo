package arun;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class vehicleController {

	public void init() {}
	@RequestMapping("/vDemo")
	public ModelAndView insertVehicle() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("arun", "hi");
		return mv;
	}
}
