package org.client.com.shoppingcart.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.client.com.shoppingcart.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartMapper {

    @Insert({"INSERT INTO shoppingcart_table values (#{s.uuid},#{s.spid},#{s.account},#{s.number})"})
    int add (@Param("s") ShoppingCart model);

    @Select({"SELECT * FROM shoppingcart_table WHERE account = #{account}"})
    List<ShoppingCart> findByAccount(@Param("account") String account);

    @Delete({"DELETE FROM shoppingcart_table WHERE account = #{account}"})
    int delCart (@Param("account")String account);

    @Delete({"DELETE FROM shoppingcart_table WHERE account = #{account} AND spid = #{spid}"})
    int delCommodity(@Param("account") String account,@Param("spid") String spid);
}
