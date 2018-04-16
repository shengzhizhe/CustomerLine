package org.client.com.order.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.client.com.order.model.OrderModel;


public interface OrderMapper {

    @Select({"select * from order_table where account like #{account}"})
    Page<OrderModel> findAllByAccount(@Param("account") String account);

    @Insert({"INSERT INTO order_table VALUES (#{model.uuid},#{model.address},#{model.phone},#{model.ddbh},#{model.cjtime}," +
            "#{model.account},#{model.busid},-1)"})
    int addOrder(@Param("model") OrderModel model);

    @Insert({"INSERT INTO order_table VALUES (#model.uuid),#{model.spid},#{model.spsl},#{model.spdj},#{model.spzj},#{model.uuid},0"})
    int addOrderSp(@Param("model") OrderModel model);
}
