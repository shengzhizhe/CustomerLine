package org.client.com.binding.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.binding.service.BinDingService;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.TokenService;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Api(value = "binding",description = "用户绑定商家")
@RestController
@RequestMapping("/binding")
public class BinDingController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private BinDingService service;
    @ApiOperation(
            value = "/binding/update/{conding}",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(value = "/binding/update/{conding}",
            method = RequestMethod.GET)
    public ResponseResult update(@PathVariable String conding,HttpServletRequest request){
        HttpServletRequest httpServletRequest = request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token_str);
        String account = byToken.getData().getAccount();
        return service.updateConding(conding,account);
    }
}
