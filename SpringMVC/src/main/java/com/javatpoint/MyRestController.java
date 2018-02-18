package com.javatpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

	@RequestMapping("/service")
	@ResponseBody
	public String service() {
		return "hello from service";
	}
}
