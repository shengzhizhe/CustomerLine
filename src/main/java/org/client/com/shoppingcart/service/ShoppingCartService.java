package org.client.com.shoppingcart.service;

import org.client.com.shoppingcart.model.ShoppingCart;
import org.client.com.util.resultJson.ResponseResult;

import java.util.List;

public interface ShoppingCartService {
    ResponseResult add(ShoppingCart modle);

    ResponseResult<List<ShoppingCart>> findByAccount(String account);

    ResponseResult creatOrder(String account);

    ResponseResult delCart(String account);
}
