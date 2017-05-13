package com.zhklong.selling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhklong.selling.service.ICityService;

/**
 * 省市控制类
 * @author paul
 * @since 2016-12-04
 * 
 * */
@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	private ICityService cityService;
	
	@RequestMapping(path="/province",method=RequestMethod.GET)
	public Object getProvice(){
		return cityService.getProvince();
	}
	
	@RequestMapping(path="/city",method=RequestMethod.GET)
	public Object getByProvince(@RequestParam("province") int province){
		return cityService.getByProvince(province);
	}

}
