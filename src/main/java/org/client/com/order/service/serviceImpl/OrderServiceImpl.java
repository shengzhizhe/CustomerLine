package org.client.com.order.service.serviceImpl;


import com.github.pagehelper.Page;
import org.client.com.order.mapper.OrderMapper;
import org.client.com.order.model.OrderModel;
import org.client.com.order.service.OrderService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.sl4j.Sl4jToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.logging.Logger;

public class OrderServiceImpl implements OrderService {

    private static Logger logger = Logger.getLogger(OrderServiceImpl.class.toString());
    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private OrderMapper mapper;

    @Override
    public List<OrderModel> findAllByAccount(String account) {
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

        return null;
    }

    @Override
    public int add(OrderModel model) {
        return 0;
    }
}
