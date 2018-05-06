package org.client.com.binding.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.client.com.util.resultJson.ResponseResult;

public interface BinDingMapper {

    @Update({"UPDATE account_table SET coding = #{conding} WHERE username = #{account}"})
    int updateConding (@Param("conding") String conding,@Param("account")String account);
}
