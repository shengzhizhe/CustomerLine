package org.client.com.register.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.login.model.LoginModel;
import org.client.com.login.service.AccountService;
import org.client.com.register.model.RegisterModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "register", description = "注册")
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "添加账户",
            response = ResponseResult.class,
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<RegisterModel> register(@Valid @RequestBody RegisterModel model,
                                                  BindingResult bindingResult) {
        ResponseResult<RegisterModel> result = new ResponseResult<>();
        if (bindingResult.hasErrors()) {
            result.setSuccess(false);
            result.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return result;
        }
        if (!model.isPass()) {
            result.setSuccess(false);
            result.setMessage("两次输入的密码不一致");
            return result;
        }

        LoginModel model1 = new LoginModel();
        model1.setUsername(model.getAccount());
        model1.setPassword(model.getPassword());

        ResponseResult<LoginModel> responseResult = accountService.add(model1);
        if (responseResult.isSuccess()) {
            result.setSuccess(true);
            result.setMessage("注册成功");
            return result;
        } else {
            result.setSuccess(false);
            result.setMessage(responseResult.getMessage());
            return result;
        }
    }

}
