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
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class CommodityServiceImpl implements CommodityService {

    private static Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);

    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private CommodityMapper mapper;

    @Override
    public ResponseResult<Page<CommodityModel>> findAllByPage(String lm,String account) {
        ResponseResult<Page<CommodityModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                 ":" + lm,
                result.getCode(),
                null));
        Page<CommodityModel> allByPage = mapper.findAllByPage(lm,account);
        result.setSuccess(true);
        result.setData(allByPage);
        result.setMessage("成功");
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                ":" + lm,
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<Page<CommodityModel>> page(String account) {
        ResponseResult<Page<CommodityModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "" ,
                result.getCode(),
                null));
        Page<CommodityModel> all = mapper.findAll(account);
        if(all.size()>0){
            result.setSuccess(true);
            result.setData(all);
            result.setMessage("成功");
        }else {
            result.setSuccess(false);
            result.setMessage("未查询到商品");
            result.setData(null);
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "",
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
            result.setMessage("抱歉，未查询到该商品");
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
    public ResponseResult add(CommodityModel model) {
        ResponseResult<CommodityModel> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.getUuid() + "",
                result.getCode(),
                null));
        int add = mapper.add(model);
        if(add==1){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.getUuid() + "",
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult del(String uuid) {
        ResponseResult<CommodityModel> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                uuid + "",
                result.getCode(),
                null));
        int del = mapper.del(uuid);
        if(del==1){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
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
    public ResponseResult<List<CommodityModel>> getByName(String name,String account) {
        ResponseResult<List<CommodityModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                name+"",
                result.getCode(),
                null));
        name = "%"+name+"%";
        List<CommodityModel> allByPage = mapper.getByName(name,account);
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
    public List<CommodityModel> findSixByLm(String lm,String account) {
        List<CommodityModel> sixByLm = mapper.findSixByLm(lm,account);
        return sixByLm;
    }

    @Override
    public ResponseResult update(CommodityModel model) {
        ResponseResult<CommodityModel> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.getCname()+"",
                result.getCode(),
                null));
        int update = mapper.update(model);
        if(update==1){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.getCname()+"",
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult updateByIdAndAcc(String spid, String account, String zt) {
        ResponseResult<String> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                spid+account+zt,
                result.getCode(),
                null));
        int update = mapper.updateByIdAndAcc(spid,account,zt);
        if(update==1){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                spid+account+zt,
                result.getCode(),
                null));
        return result;
    }
}
