package cn.message.dao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.message.dao.mapper.WxMembercardMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:junit-test.xml" })
public class TestMessageDao {

	@Autowired
	private IMessageDao messageDao;

	@Autowired
	private WxMembercardMapper userMapper;
}
