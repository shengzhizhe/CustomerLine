package org.client.com.order.service.serviceImpl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.commodity.service.CommodityService;
import org.client.com.order.mapper.OrderMapper;
import org.client.com.order.model.OrderModel;
import org.client.com.order.model.OrderSpModel;
import org.client.com.order.service.OrderService;
import org.client.com.personaldata.model.PersonalModel;
import org.client.com.personaldata.service.PersonalService;
import org.client.com.shoppingcart.model.ShoppingCart;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.sl4j.Sl4jToString;
import org.client.com.util.uuidUtil.GetUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderServiceImpl implements OrderService {

    private static Logger logger = Logger.getLogger(OrderServiceImpl.class.toString());
    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private OrderMapper mapper;
    @Autowired
    private PersonalService personalService;
    @Autowired
    private CommodityService commodityService;

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
        if (all.size() > 0) {
            result.setData(all);
            result.setSuccess(true);
        } else {
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
    public ResponseResult add(List<ShoppingCart> list) {
        ResponseResult result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                list.get(0).getAccount(),
                result.getCode(),
                null));
        ResponseResult<PersonalModel> byAccount = personalService.getByAccount(list.get(0).getAccount());
        ResponseResult<CommodityModel> byUuid = commodityService.getByUuid(list.get(0).getSpid());
        OrderModel model = new OrderModel();
        model.setUuid(GetUuid.getUUID());
        Double d = 0.0;
            for (ShoppingCart s : list) {
                OrderSpModel sp = new OrderSpModel();
                ResponseResult<CommodityModel> byUuid1 = commodityService.getByUuid(s.getSpid());
                sp.setUuid(GetUuid.getUUID());
                sp.setSpid(s.getSpid());
                sp.setSpsl(String.valueOf(s.getNumber()));
                sp.setSpdj(byUuid1.getData().getJg().toString());
                sp.setSpzj(Double.toString(s.getNumber() * byUuid1.getData().getJg()));
                sp.setOrderid(model.getUuid());
                d = d + s.getNumber() * byUuid1.getData().getJg();
                int i = mapper.addOrderSp(sp);
            }
        model.setAddress(byAccount.getData().getAddress());
        model.setPhone(byAccount.getData().getPhone());
        model.setDdbh(String.valueOf(new Date().getTime()));
        model.setCjTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        model.setAccount(list.get(0).getAccount());
        model.setBusid(byUuid.getData().getBusid());
        model.setZj(Double.toString(d));
        model.setType(-1);
        int add = mapper.addOrder(model);
        if (add == 1) {
            result.setSuccess(true);
            result.setMessage("订单生成，已通知商家，请耐心等待");
        } else {
            result.setSuccess(false);
            result.setMessage("订单生成失败");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                list.get(0).getAccount(),
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult updateOrDel(String uuid, int type) {
        ResponseResult result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                uuid,
                result.getCode(),
                null));
        int del = mapper.updateOrDel(uuid, type);
        if (del == 1) {
            result.setSuccess(true);
        } else {
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
        if (update > 0) {
            result.setSuccess(true);
        } else {
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
    public ResponseResult<Page<OrderModel>> page(int pageNum, int pageSize, String account, String type) {
        ResponseResult<Page<OrderModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        PageHelper.startPage(pageNum, pageSize);
        Page<OrderModel> page = new Page<>();
        if (type == null || "".equals(type)) {
            page = mapper.page(account);
        } else {
            page = mapper.page1(account, type);
        }
        if (page.size() > 0) {
            result.setSuccess(true);
            result.setData(page);
        } else {
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
