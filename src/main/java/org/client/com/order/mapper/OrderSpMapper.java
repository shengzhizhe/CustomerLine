package org.client.com.order.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.client.com.order.model.OrderSpModel;

public interface OrderSpMapper {

    @Select({"SELECT o.uuid,c.cname spid,o.spsl,o.spdj,o.spzj,c.dw dw,c.ge ge,c.pp pp,o.orderid,o.zt FROM ordersp_table o LEFT JOIN commodity_table" +
            " c ON c.uuid = o.spid WHERE orderid = #{orid}"})
    Page<OrderSpModel> page(@Param("orid") String orid);
}
