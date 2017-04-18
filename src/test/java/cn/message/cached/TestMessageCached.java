package cn.message.cached;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.message.cached.IMessageCached;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:junit-test.xml"})
public class TestMessageCached {
	
	
	@Autowired
	private IMessageCached messageCahce;
	
	@BeforeClass
	public static void setup(){
		//测试开始，可以做一些准备工作，比如清除可能存在的冲突key等
	 	System.out.println("prepare for test start");
	}	
	@AfterClass
	public static void teardown(){
		//测试结束,将本次测试的数据进行清理，确保单元测试可以二次运行
		System.out.println("finish test,clear data");
	}
}
