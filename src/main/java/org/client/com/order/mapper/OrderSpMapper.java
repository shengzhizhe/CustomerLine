package org.client.com.order.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.client.com.order.model.OrderSpModel;

public interface OrderSpMapper {

    @Select({"SELECT * FROM ordersp_table WHERE orderid = #{orid}"})
    Page<OrderSpModel> page(@Param("orid") String orid);
}
