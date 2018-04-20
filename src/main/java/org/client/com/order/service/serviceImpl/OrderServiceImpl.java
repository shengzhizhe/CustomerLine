package org.client.com.order.service.serviceImpl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.client.com.order.mapper.OrderMapper;
import org.client.com.order.model.OrderModel;
import org.client.com.order.service.OrderService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.sl4j.Sl4jToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
@Service
public class OrderServiceImpl implements OrderService {

    private static Logger logger = Logger.getLogger(OrderServiceImpl.class.toString());
    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private OrderMapper mapper;

    @Override
    public ResponseResult<Page<OrderModel>> findAllByAccount(String account) {
        ResponseResult<Page<OrderModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        Page<OrderModel> all = mapper.findAllByAccount(account);
        if(all.size()>0){
            result.setData(all);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setMessage("未找到订单");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));

        return result;
    }

    @Override
    public List<OrderModel> findByType(String account) {
        ResponseResult<List<OrderModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                200,
                null));
        List<OrderModel> byType = mapper.findByType(account);
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                200,
                null));
        return byType;
    }

    @Override
    public ResponseResult add(OrderModel model) {
        ResponseResult result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.getAccount(),
                result.getCode(),
                null));
        int add = mapper.addOrder(model);
        int i = mapper.addOrderSp(model);
        if(add>0){
            result.setData(add);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setMessage("未找到订单");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.getAccount(),
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult updateOrDel(String uuid,int type) {
        ResponseResult result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                uuid,
                result.getCode(),
                null));
        int del = mapper.updateOrDel(uuid,type);
        if(del==1){
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                uuid,
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult update(String account) {
        ResponseResult result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        int update = mapper.update(account);
        if(update>0){
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<Page<OrderModel>> page(int pageNum, int pageSize, String account,String type) {
        ResponseResult<Page<OrderModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        PageHelper.startPage(pageNum,pageSize);
        Page<OrderModel> page = new Page<>();
        if(type==null||"".equals(type)){
            page = mapper.page(account);
        }else {
            page = mapper.page1(account,type);
        }
        if(page.size()>0){
            result.setSuccess(true);
            result.setData(page);
        }else{
            result.setSuccess(false);
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        return result;
    }
}
