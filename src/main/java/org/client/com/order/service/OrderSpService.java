package org.client.com.order.service;



import com.github.pagehelper.Page;
import org.client.com.order.model.OrderModel;
import org.client.com.order.model.OrderSpModel;
import org.client.com.util.resultJson.ResponseResult;

import java.util.List;

public interface OrderSpService {

    ResponseResult<Page<OrderSpModel>> page (int pageNum, int pageSize, String orid);
    ResponseResult<Page<OrderSpModel>> page1 (String orid);

    int update(OrderSpModel model);

}
