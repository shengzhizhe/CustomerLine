package org.client.com.shoppingcart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.shoppingcart.model.ShoppingCart;
import org.client.com.shoppingcart.service.ShoppingCartService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.uuidUtil.GetUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/cart",description = "购物车")
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService service;

    @ApiOperation(
            value = "向购物车添加",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/add/{spid}/{num}/{account}",
            method = RequestMethod.GET)
    public ResponseResult add(@PathVariable("spid")String spid,
                              @PathVariable("num")int num,
                              @RequestParam("account")String account){
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
            value = "/findByAccount/{account}",
            method = RequestMethod.GET)
    public ResponseResult findByAccount(@RequestParam("account")String account){
        return service.findByAccount(account);
    }

    @ApiOperation(
            value = "生成订单",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/creatOrder/{account}",
            method = RequestMethod.GET)
    public ResponseResult creatOrder(@RequestParam("account")String account){
        return service.creatOrder(account);
    }

    @ApiOperation(
            value = "清空购物车",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/delCart/{account}",
            method = RequestMethod.GET)
    public ResponseResult delCart(@RequestParam("account")String account){
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
            @RequestParam("account")String account,
            @RequestParam("spid")String spid){
        return service.delCommodity(account,spid);
    }
}
