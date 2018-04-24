package org.client.com.order.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.client.com.order.model.OrderModel;
import org.client.com.order.model.OrderSpModel;
import org.client.com.util.resultJson.ResponseResult;

import java.util.List;


public interface OrderMapper {

    @Select({"select * from order_table where account like #{account}"})
    Page<OrderModel> findAllByAccount(@Param("account") String account);

    @Select({"select * from order_table where busid = #{account}"})
    Page<OrderModel> page(@Param("account") String account);

    @Select({"select * from order_table where busid = #{account} and type = #{type}"})
    Page<OrderModel> page1(@Param("account") String account,@Param("type")String type);

    @Insert({"INSERT INTO order_table VALUES (#{model.uuid},#{model.zj},#{model.address},#{model.phone},#{model.ddbh},#{model.cjtime}," +
            "#{model.account},#{model.busid},-1)"})
    int addOrder(@Param("model") OrderModel model);

    @Insert({"INSERT INTO order_table VALUES (#model.uuid),#{model.spid},#{model.spsl},#{model.spdj},#{model.spzj},#{model.orderid},0"})
    int addOrderSp(@Param("model") OrderSpModel model);

    @Update({"UPDATE order_table SET type = #{type} WHERE uuid = #{uuid}"})
    int updateOrDel(@Param("uuid")String uuid,@Param("type")int type);

    @Select({"select * from order_table where busid = #{account} and type = -1"})
    List<OrderModel> findByType(@Param("account") String account);

    @Update({"UPDATE order_table SET type = 0 WHERE busid = #{account} and type = -1"})
    int update(@Param("account") String account);
}
