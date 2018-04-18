package org.client.com.order.service;

import com.github.pagehelper.Page;
import org.client.com.order.model.OrderModel;
import org.client.com.util.resultJson.ResponseResult;

import java.util.List;

public interface OrderService {

    ResponseResult<Page<OrderModel>> findAllByAccount(String account);

    ResponseResult add(OrderModel model);

    ResponseResult del(String uuid);

    ResponseResult<Page<OrderModel>> page(int pageNum,int pageSize,String account,String type);
}
