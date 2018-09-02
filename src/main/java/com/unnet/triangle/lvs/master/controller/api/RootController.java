package com.unnet.triangle.lvs.master.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {
	@ResponseBody
	  @GetMapping("/")
	  public String index() {
	    return "This is triangle LVS service";
	  }
}
