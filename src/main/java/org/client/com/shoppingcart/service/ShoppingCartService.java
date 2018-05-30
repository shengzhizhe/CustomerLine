package org.client.com.shoppingcart.service;

import org.client.com.commodity.model.CommodityModel;
import org.client.com.shoppingcart.model.ShoppingCart;
import org.client.com.util.resultJson.ResponseResult;

import java.util.List;

public interface ShoppingCartService {
    ResponseResult add(ShoppingCart modle);

    ResponseResult<List<ShoppingCart>> findByAccount(String account);

    ResponseResult creatOrder(String account, List<ShoppingCart> s);

    ResponseResult delCart(String account);

    ResponseResult delCommodity(String account,String spid);
}
