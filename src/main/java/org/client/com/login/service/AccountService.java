package org.client.com.login.service;

import com.github.pagehelper.Page;
import org.client.com.login.model.LoginModel;
import org.client.com.util.resultJson.ResponseResult;

/**
 * @name 账户接口
 */
public interface AccountService {
    /**
     * 新增账户
     *
     * @param model
     * @return int
     */
    ResponseResult<LoginModel> add(LoginModel model);

    /**
     * 修改密码
     *
     * @param account
     * @param password
     * @return
     */
    ResponseResult<LoginModel> putPWD(String account, String password);

    /**
     * 根据id获取实体
     *
     * @param id
     * @return
     */
    ResponseResult<LoginModel> getById(String id);

    /**
     * 根据账户获取实体
     *
     * @param account
     * @return
     */
    ResponseResult<LoginModel> getByAccount(String account);

    ResponseResult<LoginModel> getByAccount2(String account, String types);

    /**
     * 根据用户账户的绑定号码获取商家实体
     *
     * @param account
     * @return
     */
    ResponseResult<LoginModel> getByCoding(String account);

    /**
     * 根据类型获取账户
     *
     * @return
     */
    ResponseResult<Page<LoginModel>> findAllPage(int pageNow, int pageSize, String type, String account);

    /**
     * 根据id删除实体
     *
     * @param id
     * @return
     */
    ResponseResult<LoginModel> del(String id);

}
