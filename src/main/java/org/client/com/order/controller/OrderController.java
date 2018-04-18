package org.client.com.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.order.model.OrderModel;
import org.client.com.order.service.OrderService;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.web.bind.annotation.*;


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

    @ApiOperation(
            value = "生成账单",
            response = ResponseResult.class,
            httpMethod = "POST"
    )
    @PostMapping(value = "/order")
    public ResponseResult add(@RequestBody OrderModel model){
        ResponseResult add = service.add(model);
        return add;
    }
}
