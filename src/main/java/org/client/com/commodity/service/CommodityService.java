package org.client.com.commodity.service;

import com.github.pagehelper.Page;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.util.resultJson.ResponseResult;

import java.util.List;

public interface CommodityService {

    ResponseResult<Page<CommodityModel>> findAllByPage(int pageNow, int pageSize, String lm);

    ResponseResult<Page<CommodityModel>> page(int pageNow, int pageSize,String account);

    ResponseResult<CommodityModel> getByUuid(String uuid);

    ResponseResult add(CommodityModel model);
    ResponseResult del(String uuid);

    ResponseResult<List<CommodityModel>> getByName(String name);

    List<CommodityModel> findSixByLm (String lm);

    ResponseResult update(CommodityModel model);

    ResponseResult updateByIdAndAcc (String spid,String account,String zt);
}
