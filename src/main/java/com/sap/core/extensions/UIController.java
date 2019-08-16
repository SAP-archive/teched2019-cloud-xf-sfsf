package com.sap.core.extensions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {

	@RequestMapping(path= {"/", "index.html"})
	public String index() {
		return "index";
	}
	
}
