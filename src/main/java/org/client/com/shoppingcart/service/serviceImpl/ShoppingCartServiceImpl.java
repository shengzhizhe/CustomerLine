package org.client.com.shoppingcart.service.serviceImpl;

import org.client.com.order.service.OrderService;
import org.client.com.order.service.serviceImpl.OrderServiceImpl;
import org.client.com.shoppingcart.mapper.ShoppingCartMapper;
import org.client.com.shoppingcart.model.ShoppingCart;
import org.client.com.shoppingcart.service.ShoppingCartService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.sl4j.Sl4jToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static Logger logger = Logger.getLogger(OrderServiceImpl.class.toString());
    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private ShoppingCartMapper mapper;
    @Autowired
    private OrderService service;
    @Override
    public ResponseResult add(ShoppingCart model) {
        ResponseResult<String> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.getAccount()+model.getSpid()+model.getNumber(),
                result.getCode(),
                null));
        int add = mapper.add(model);
        if(add==1){
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setMessage("未添加成功，请稍后再试");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.getAccount()+model.getSpid()+model.getNumber(),
                result.getCode(),
                null));

        return result;
    }

    @Override
    public ResponseResult<List<ShoppingCart>> findByAccount(String account) {
        ResponseResult<List<ShoppingCart>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account+"",
                result.getCode(),
                null));
        List<ShoppingCart> byAccount = mapper.findByAccount(account);
        if(byAccount.size()>0){
            result.setSuccess(true);
            result.setData(byAccount);
        }else{
            result.setSuccess(false);
            result.setMessage("购物车是空的");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account+"",
                result.getCode(),
                null));

        return result;
    }

    @Override
    public ResponseResult creatOrder(String account) {
        ResponseResult<String> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account+"",
                result.getCode(),
                null));
        ResponseResult<List<ShoppingCart>> byAccount = findByAccount(account);
        ResponseResult add = service.add(byAccount.getData());
        if(add.isSuccess()){
            mapper.delCart(account);
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account+"",
                result.getCode(),
                null));

        return add;
    }

    @Override
    public ResponseResult delCart(String account) {
        ResponseResult<String> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account+"",
                result.getCode(),
                null));
        int i = mapper.delCart(account);
        if(i>0){
            result.setSuccess(true);
            result.setMessage("已清空购物车");
        }else {
            result.setSuccess(false);
            result.setMessage("系统繁忙，请稍后再试");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account+"",
                result.getCode(),
                null));

        return result;
    }

    @Override
    public ResponseResult delCommodity(String account, String spid) {
        ResponseResult<String> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account+"",
                result.getCode(),
                null));
        int i = mapper.delCommodity(account,spid);
        if(i == 1){
            result.setSuccess(true);
            result.setMessage("已移除");
        }else {
            result.setSuccess(false);
            result.setMessage("未移除成功，请稍后再试");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account+"",
                result.getCode(),
                null));

        return result;
    }


}
