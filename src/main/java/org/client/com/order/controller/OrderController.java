package org.client.com.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.order.service.OrderService;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "order",description = "订单")
@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService service;

    @ApiOperation(
            value = "根据用户账号获取所有订单",
            response = ResponseResult.class,
            httpMethod = "GET")
    @GetMapping(value = "/order/findAllByAccount/{account}")
    public ResponseResult findAllByAccount(@PathVariable String account){
        service.findAllByAccount(account);
        return null;
    }
    public ResponseResult add(){
        return null;
    }
}
