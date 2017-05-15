package com.zhklong.selling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhklong.selling.service.impl.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(path="/getAll",method=RequestMethod.GET)
	public @ResponseBody Object getAll(){
		return roleService.getAll().get();
	}
	
	@RequestMapping(path="/addFunc",method=RequestMethod.GET)
	public @ResponseBody Object addFunctionality2Role(@RequestParam("roleid") int roleid,@RequestParam("funcid") int funcid){
		return roleService.addFunctionality(roleid, funcid).get();
	}
	
	@RequestMapping(path="/removeFunc",method=RequestMethod.GET)
	public @ResponseBody Object removeFunctionality(@RequestParam("roleid") int roleid,@RequestParam("funcid") int funcid){
		return roleService.removeFunctionality(roleid, funcid).get();
	}
	
	@RequestMapping(path="/getFunc",method=RequestMethod.GET)
	public @ResponseBody Object getFunctionality(@RequestParam("roleid") int roleid){
		return roleService.getFunctionality(roleid).get();
	}
	
	
	@RequestMapping(path="/getNotIn",method=RequestMethod.GET)
	public @ResponseBody Object getFunctionalityNotIn(@RequestParam("roleid") int roleid){
		return roleService.getNotInRole(roleid).get();
	}
	
	

}
