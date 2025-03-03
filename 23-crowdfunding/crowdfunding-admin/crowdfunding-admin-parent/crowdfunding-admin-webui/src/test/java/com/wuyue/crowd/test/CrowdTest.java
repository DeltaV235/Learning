package com.wuyue.crowd.test;

import com.wuyue.crowd.mapper.AdminMapper;
import com.wuyue.crowd.service.inter.AdminService;
import com.wuyue.util.CrowdUtil;
import entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author DeltaV235
 * @version 1.0
 * @className CrowdTest
 * @description
 * @date 2020/5/2 23:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-persist-mybatis.xml", "classpath:/spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void insertFakeData() {
        for (int i = 0; i < 3; i++) {
            adminMapper.insert(new Admin(null,
                    "loginAcct" + i,
                    CrowdUtil.md5("userPswd" + i),
                    "userName" + i,
                    "email" + i,
                    null));
        }
    }

    @Test
    public void testMd5() {
        String source = "123123";
        String encoded = CrowdUtil.md5(source);
        logger.info(encoded);
    }

    @Test
    public void testTX() {
        Admin admin = new Admin(null, "jerry", "jerrypass", "jerry", "jerry@cat.com", "null");
        adminService.saveAdmin(admin);
    }

    @Test
    public void testLogger() {
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

    @Test
    public void testMapper() {
        Admin admin = new Admin(null, "tom", "tompass", "tomcat", "tomcat@cat.com", "null");
        int insert = adminMapper.insert(admin);
        System.out.println(insert);
    }

    @Test
    public void testDatasource() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
