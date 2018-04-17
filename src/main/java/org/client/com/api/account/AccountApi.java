package org.client.com.api.account;

import org.client.com.login.model.LoginModel;
import org.client.com.login.service.AccountService;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
@RestController
@RequestMapping("/api/account")
public class AccountApi {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean login(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("types") int types) {
        ResponseResult<LoginModel> result = accountService.getByAccount2(username, types + "");
        if (result.isSuccess()) {
            if (password.equals(result.getData().getPassword()))
                return true;
            else
                return false;
        } else
            return false;
    }
}
