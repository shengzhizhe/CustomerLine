package org.client.com.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.TokenService;
import org.client.com.order.service.OrderService;
import org.client.com.order.service.OrderSpService;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


@Api(value = "order",description = "订单")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService service;
    @Autowired
    private OrderSpService orderSpService;
    @Autowired
    private TokenService tokenService;

    @ApiOperation(
            value = "根据用户账号获取所有订单",
            response = ResponseResult.class,
            httpMethod = "GET")
    @GetMapping(value = "/order/findAllByAccount")
    public ResponseResult findAllByAccount(ServletRequest request){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token_str);
        String account = byToken.getData().getAccount();
        return service.findAllByAccount(account);
    }

    @ApiOperation(
            value = "根据订单主键查找订单商品",
            response = ResponseResult.class,
            httpMethod = "GET")
    @GetMapping(value = "/order/findAllByOrid/{orid}")
    public ResponseResult findAllByOrid(@RequestParam("orid")String orid){
        return orderSpService.page1(orid);
    }

//    @ApiOperation(
//            value = "生成账单",
//            response = ResponseResult.class,
//            httpMethod = "POST"
//    )
//    @PostMapping(value = "/order")
//    public ResponseResult add(@RequestBody OrderModel model){
//        ResponseResult add = service.add(model);
//        return add;
//    }
}
