package org.client.com.api;

import org.client.com.Application;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.TokenService;
import org.client.com.login.service.impl.TokenServiceImpl;
import org.client.com.util.resultJson.ResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AccountInterfaceTest {

    private final static Logger logger = LoggerFactory.getLogger(AccountInterfaceTest.class);


    @Test
    public void test() {
        TokenService service = new TokenServiceImpl();
//        ResponseResult<TokenModel> result = service.getByToken2("62c998dc73b34d16ad43ff6cba6d5404");
//        ResponseResult<TokenModel> result = service.updateToken2("62c998dc73b34d16ad43ff6cba6d5404");
        TokenModel model = new TokenModel();
        model.setEndTimes(System.currentTimeMillis());
        model.setIsUse("123");
        model.setToken("1234");
        model.setAccount("12345");
        ResponseResult<TokenModel> result = service.add2(model);
        System.out.println(result);
    }
}