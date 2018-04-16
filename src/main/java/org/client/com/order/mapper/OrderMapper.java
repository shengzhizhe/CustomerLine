package org.client.com.order.mapper;

import com.github.pagehelper.Page;
import org.client.com.order.model.OrderModel;


public interface OrderMapper {

    Page<OrderModel> findAllByAccount(String account);

    int add(OrderModel model);
}
