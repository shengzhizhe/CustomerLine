package org.client.com.category.service.serviceImpl;

import com.github.pagehelper.Page;
import org.client.com.category.mapper.CategoryMapper;
import org.client.com.category.model.CategoryModel;
import org.client.com.category.service.CategoryService;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.commodity.service.serviceImpl.CommodityServiceImpl;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.sl4j.Sl4jToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    private static Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);

    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private CategoryMapper mapper;

    @Override
    public ResponseResult<String> findAll() {
        ResponseResult<String> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "",
                200,
                null));
        List<CategoryModel> all = mapper.findAll();
        String ca = "";
        for (CategoryModel c:all){
            ca += c.getCnames()+",";
        }
        result.setSuccess(true);
        result.setData(ca);
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "",
                200,
                null));
        return result;
    }

    @Override
    public List<CategoryModel> findAll2() {
        logger.info(Sl4jToString.info(
                1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "",
                200,
                null));
        List<CategoryModel> all = mapper.findAll();
        logger.info(Sl4jToString.info(
                2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "",
                200,
                null));
        return all;
    }
}
