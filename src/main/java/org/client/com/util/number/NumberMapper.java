package org.client.com.util.number;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NumberMapper {
    @Select({"select number from number_table"})
    int getNumber();

    @Update({"update number_table set number = #{num}"})
    int updateNumber(@Param("num") int number);
}
