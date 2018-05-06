package org.client.com.binding.service.serviceImpl;

import org.client.com.binding.mapper.BinDingMapper;
import org.client.com.binding.service.BinDingService;
import org.client.com.commodity.service.serviceImpl.CommodityServiceImpl;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.sl4j.Sl4jToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BinDingServiceImpl implements BinDingService {

    private static Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);

    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private BinDingMapper mapper;

    @Override
    public ResponseResult updateConding(String conding,String account) {
        ResponseResult<String> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                conding+"",
                result.getCode(),
                null));
        int update = mapper.updateConding(conding,account);
        if(update==1){
            result.setSuccess(true);
            result.setMessage("绑定成功");
        }else {
            result.setSuccess(false);
            result.setMessage("绑定失败");
        }
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                conding+"",
                result.getCode(),
                null));
        return result;
    }
}
