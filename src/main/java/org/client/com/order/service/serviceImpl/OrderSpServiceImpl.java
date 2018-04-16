package org.client.com.order.service.serviceImpl;


import org.client.com.order.model.OrderSpModel;
import org.client.com.order.service.OrderSpService;

import java.util.StringJoiner;
import java.util.logging.Logger;

public class OrderSpServiceImpl implements OrderSpService {

    private static Logger logger = Logger.getLogger(OrderSpServiceImpl.class.toString());

    @Override
    public int update(OrderSpModel model) {
        OrderSpDao dao = new OrderSpDaoImpl();
        StringJoiner sql = new StringJoiner("");
        sql.add("update order_table set zt='" + model.getZt() + "' where uuid = '" + model.getUuid() + "'");
        logger.info(sql.toString());
        return dao.data(sql.toString());
    }
}
