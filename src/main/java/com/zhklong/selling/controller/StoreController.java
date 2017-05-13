package com.zhklong.selling.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhklong.selling.service.IStoreService;

/**
 * @author paul
 * @since 2016-11-30
 * 门店控制类
 * */
@Controller
@RequestMapping("/store")
public class StoreController {
	
	private static final Logger logger = Logger.getLogger(StoreController.class);
	
	@Autowired
	private IStoreService storeService;
	
	/**
	 * 根据公司id得到门店
	 * @param company 公司id
	 * 
	 * */
	@RequestMapping(path="/getByCompany",method=RequestMethod.GET)
	@ResponseBody
	public Object getByCompany(@RequestParam("company") int company){
		logger.info("company : " + company);
		return storeService.getByCompany(company);
	}

}
