package com.zhklong.selling.mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhklong.selling.BaseTest;
import com.zhklong.selling.entity.Functionality;

public class FunctionalityMapperTest extends BaseTest{
	
	@Autowired
	private FunctionalityMapper fm;
	
	@Test
	public void testGet(){
		List<Functionality> list = fm.getByEmployee(1);
		for(Functionality func:list){
			System.out.println(func.getName());
		}
	}

}
