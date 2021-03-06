package org.client.com.login.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.client.com.login.mapper.sql.AccountSql;
import org.client.com.login.model.LoginModel;


/**
 * //@name 人员资料
 */
public interface AccountMapper {

    String tableName = " account_table ";

    /**
     * 新增账户
     *
     * @param model AccountModel
     * @return int
     */
    @Insert({
            "insert into " + tableName + " (username,password,types,coding) " +
                    "values (#{model.username},#{model.password},#{model.types},#{model.coding})"
    })
    int add(@Param("model") LoginModel model);

    /**
     * 修改密码
     *
     * @param username String
     * @param password String
     * @return int
     */
    @Update({
            "update" + tableName + " set password = #{password} where username = #{username}"
    })
    int putPWD(@Param("username") String username, @Param("password") String password);

    /**
     * 根据username获取实体
     *
     * @param username String
     * @return AccountModel
     */
    @Select({
            "select * from " + tableName + " where username = #{username}"
    })
    LoginModel getByUsername(@Param("username") String username);
    /**
     * 根据username获取实体
     *
     * @param username String
     * @return AccountModel
     */
    @Select({
            "select a.username,a.`password`,a.types,a.coding from account_table a WHERE a.coding = ( SELECT coding FROM " +
                    "account_table WHERE username = #{username}) AND types = 1"
    })
    LoginModel getByCoding(@Param("username") String username);

    /**
     * 获取所有的指定类型的账户
     *
     * @param type    String
     * @param account String
     * @return Page<AccountModel>
     */
    @SelectProvider(type = AccountSql.class, method = "select_page_type")
    Page<LoginModel> findAllPage(@Param("type") String type, @Param("account") String account);

    /**
     * 根据username删除实体
     *
     * @param username String
     * @return int
     */
    @Delete({
            "delete from account_table where username = #{username}"
    })
    int del(@Param("username") String username);
}
