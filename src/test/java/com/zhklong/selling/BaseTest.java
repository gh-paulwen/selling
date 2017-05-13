package com.zhklong.selling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-tx.xml","classpath:spring/spring-service.xml","classpath:spring/spring-util.xml"})
public class BaseTest {
	
	@Test
	public void testEmpty(){
		
	}
	
}
