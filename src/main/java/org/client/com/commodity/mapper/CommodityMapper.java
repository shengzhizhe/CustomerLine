package org.client.com.commodity.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.client.com.commodity.model.CommodityModel;

import java.util.List;

public interface CommodityMapper {

    @Select({"SELECT * FROM commodity_table WHERE lm = #{lm}"})
    Page<CommodityModel> findAllByPage(@Param("lm") String lm);

    @Select({"SELECT * FROM commodity_table WHERE uuid = #{uuid}"})
    CommodityModel getByUuid(@Param("uuid") String uuid);

    @Select({"SELECT * FROM commodity_table WHERE cname LIKE #{uuid}"})
    List<CommodityModel> getByName (String name);

    @Select({"SELECT * FROM commodity_table WHERE lm = #{lm} LIMIT 6 "})
    List<CommodityModel> findSixByLm (@Param("lm") String lm);
}
