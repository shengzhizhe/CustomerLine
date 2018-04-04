package org.client.com.commodity.service;

import com.github.pagehelper.Page;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.util.resultJson.ResponseResult;

public interface CommodityService {

    ResponseResult<Page<CommodityModel>> findAllByPage(int pageNow, int pageSize, String lm);

    ResponseResult<CommodityModel> getByUuid(String uuid);
}
