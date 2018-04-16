package org.client.com.order.service;

import org.client.com.order.model.OrderModel;

import java.util.List;

public interface OrderService {

    List<OrderModel> findAllByAccount(String account);

    int add(OrderModel model);
}
