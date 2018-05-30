package org.client.com.shoppingcart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.TokenService;
import org.client.com.shoppingcart.model.ShoppingCart;
import org.client.com.shoppingcart.service.ShoppingCartService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.uuidUtil.GetUuid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "/cart", description = "购物车")
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService service;
    @Autowired
    private TokenService tokenService;

    @ApiOperation(
            value = "向购物车添加",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/add/{spid}/{num}",
            method = RequestMethod.GET)
    public ResponseResult add(@PathVariable("spid") String spid,
                              @PathVariable("num") int num,
                              HttpServletRequest request) {
        HttpServletRequest httpServletRequest = request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken2(token_str);
        String account = byToken.getData().getAccount();
        ShoppingCart model = new ShoppingCart();
        model.setUuid(GetUuid.getUUID());
        model.setSpid(spid);
        model.setAccount(account);
        model.setNumber(num);
        return service.add(model);
    }

    @ApiOperation(
            value = "查询购物车",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/findByAccount",
            method = RequestMethod.GET)
    public ResponseResult findByAccount(HttpServletRequest request) {
        HttpServletRequest httpServletRequest = request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken2(token_str);
        String account = byToken.getData().getAccount();
        return service.findByAccount(account);
    }

    @ApiOperation(
            value = "生成订单",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/creatOrder",
            method = RequestMethod.POST)
    public ResponseResult creatOrder(HttpServletRequest request,@RequestBody List<ShoppingCart> s) {
        HttpServletRequest httpServletRequest = request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken2(token_str);
        String account = byToken.getData().getAccount();
        return service.creatOrder(account,s);
    }

    @ApiOperation(
            value = "清空购物车",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/delCart",
            method = RequestMethod.GET)
    public ResponseResult delCart(HttpServletRequest request) {
        HttpServletRequest httpServletRequest = request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken2(token_str);
        String account = byToken.getData().getAccount();
        return service.delCart(account);
    }

    @ApiOperation(
            value = "删除购物车中的某件商品",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/delCommodity/{account}/{spid}",
            method = RequestMethod.GET)
    public ResponseResult delCommodity(
            @RequestParam("spid") String spid, HttpServletRequest request) {
        HttpServletRequest httpServletRequest = request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken2(token_str);
        String account = byToken.getData().getAccount();
        return service.delCommodity(account, spid);
    }
}
