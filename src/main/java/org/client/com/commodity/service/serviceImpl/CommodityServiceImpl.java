package org.client.com.commodity.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.client.com.commodity.mapper.CommodityMapper;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.commodity.service.CommodityService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.sl4j.Sl4jToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * logger.info(Sl4jToString.info(1,
 * serviceName,
 * Thread.currentThread().getStackTrace()[1].getMethodName(),
 * model.toString(),
 * 200,
 * null));
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    private static Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);

    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private CommodityMapper mapper;

    @Override
    public ResponseResult<Page<CommodityModel>> findAllByPage(int pageNow, int pageSize, String lm) {
        ResponseResult<Page<CommodityModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                pageNow + ":" + pageSize + ":" + lm,
                result.getCode(),
                null));
        PageHelper.startPage(pageNow, pageSize);
        Page<CommodityModel> allByPage = mapper.findAllByPage(lm);
        result.setSuccess(true);
        result.setData(allByPage);
        result.setMessage("成功");
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                pageNow + ":" + pageSize + ":" + lm,
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<CommodityModel> getByUuid(String uuid) {
        ResponseResult<CommodityModel> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                uuid + "",
                result.getCode(),
                null));
        CommodityModel byName = mapper.getByUuid(uuid);
        if (byName == null || "".equals(byName)) {
            result.setSuccess(false);
            result.setMessage("抱歉，该商品已下架");
        } else {
            result.setSuccess(true);
            result.setMessage("成功");
            result.setData(byName);
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                uuid + "",
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<List<CommodityModel>> getByName(String name) {
        ResponseResult<List<CommodityModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                name+"",
                result.getCode(),
                null));
        name = "%"+name+"%";
        List<CommodityModel> allByPage = mapper.getByName(name);
        if(allByPage.size()>0){
            result.setSuccess(true);
            result.setData(allByPage);
            result.setMessage("成功");
        }else {
            result.setSuccess(false);
            result.setMessage("未查询到相关商品");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                name+"",
                result.getCode(),
                null));
        return result;
    }

    @Override
    public List<CommodityModel> findSixByLm(String lm) {
        List<CommodityModel> sixByLm = mapper.findSixByLm(lm);
        return sixByLm;
    }


}
