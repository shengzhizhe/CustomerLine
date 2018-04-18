package org.client.com.order.service.serviceImpl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.client.com.order.mapper.OrderSpMapper;
import org.client.com.order.model.OrderModel;
import org.client.com.order.model.OrderSpModel;
import org.client.com.order.service.OrderSpService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.sl4j.Sl4jToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;
import java.util.logging.Logger;
@Service
public class OrderSpServiceImpl implements OrderSpService {

    private static Logger logger = Logger.getLogger(OrderSpServiceImpl.class.toString());
    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private OrderSpMapper mapper;

    @Override
    public ResponseResult<Page<OrderSpModel>> page(int pageNum, int pageSize, String orid) {
        ResponseResult<Page<OrderSpModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                orid,
                result.getCode(),
                null));
        PageHelper.startPage(pageNum,pageSize);
        Page<OrderSpModel> page = mapper.page(orid);
        if(page.size()>0){
            result.setSuccess(true);
            result.setData(page);
        }else{
            result.setSuccess(false);
            result.setMessage("未查询到数据");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                orid,
                result.getCode(),
                null));
        return result;
    }

    @Override
    public int update(OrderSpModel model) {
//        OrderSpDao dao = new OrderSpDaoImpl();
//        StringJoiner sql = new StringJoiner("");
//        sql.add("update order_table set zt='" + model.getZt() + "' where uuid = '" + model.getUuid() + "'");
//        logger.info(sql.toString());
//        return dao.data(sql.toString());
        return 0;
    }
}
