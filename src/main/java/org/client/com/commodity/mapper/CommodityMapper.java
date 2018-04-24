package org.client.com.commodity.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CommodityMapper {

    @Select({"SELECT * FROM commodity_table WHERE lm = #{lm}"})
    Page<CommodityModel> findAllByPage(@Param("lm") String lm);

    @Select({"SELECT * FROM commodity_table WHERE busid = #{account}"})
    Page<CommodityModel> findAll(@Param("account") String account);

    @Select({"SELECT * FROM commodity_table WHERE uuid = #{uuid}"})
    CommodityModel getByUuid(@Param("uuid") String uuid);

    @Select({"SELECT * FROM commodity_table WHERE cname LIKE #{uuid}"})
    List<CommodityModel> getByName (String name);

    @Select({"SELECT * FROM commodity_table WHERE lm = #{lm} LIMIT 6 "})
    List<CommodityModel> findSixByLm (@Param("lm") String lm);

    @Insert({"INSERT INTO commodity_table VALUES (#{model.uuid},#{model.cname},#{model.jg},#{model.dw},#{model.ge}," +
            "#{model.zt},#{model.pp},#{model.xq},#{model.xl},#{model.busid},#{model.sl},#{model.lm},#{model.sxj})"})
    int add(@Param("model") CommodityModel model);

    @Update({"UPDATE commodity_table SET cname=#{model.cname},jg=#{model.jg},dw=#{model.dw}," +
            "ge=#{model.ge},zt=#{model.zt},pp=#{model.pp},xq=#{model.xq},xl=#{model.xl},busid=#{model.busid},sl=#{model.sl}," +
            "lm=#{model.lm},sxj=#{model.sxj} WHERE uuid = #{model.uuid}"})
    int update(@Param("model") CommodityModel model);

    @Delete({"DELETE FROM commodity_table WHERE uuid = #{uuid}"})
    int del (@Param("uuid") String uuid);

    @Update({"UPDATE commodity_table SET zt = #{zt} WHERE uuid = #{spid} AND busid = #{account}"})
    int updateByIdAndAcc (@Param("spid") String spid,@Param("account") String account,@Param("zt") String zt);
}
