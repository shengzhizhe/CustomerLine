package org.client.com.commodity.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.category.model.CategoryModel;
import org.client.com.category.service.CategoryService;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.commodity.service.CommodityService;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "commodity", description = "商品")
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityService service;
    @Autowired
    private CategoryService categoryService;

    public CommodityController() {
    }

    @ApiOperation(
            value = "查询多个商品并且分页",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/commodity/page/{lm}",
            method = RequestMethod.GET)
    public ResponseResult page(@PathVariable(value = "lm") String lm) {
        return service.findAllByPage( lm);
    }

    @ApiOperation(
            value = "根据主键精准查询",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/commodity/getByUuid/{uuid}",
            method = RequestMethod.GET)
    public ResponseResult getByUuid(@PathVariable(value = "uuid") String uuid) {
        return service.getByUuid(uuid);
    }

    @ApiOperation(
            value = "根据名称模糊查询",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/commodity/getByName/{name}",
            method = RequestMethod.GET)
    public ResponseResult getByName(@PathVariable(value = "name") String name) {
        return service.getByName(name);
    }

    /**
     * @return ResponseResult
     */
    @ApiOperation(
            value = "根据类型查询出六个",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/commodity/findSixByLm",
            method = RequestMethod.GET)
    public ResponseResult findSixByLm() {
        ResponseResult<List<List<CommodityModel>>> result = new ResponseResult<>();
        List<CategoryModel> all = categoryService.findAll();
        ArrayList<List<CommodityModel>> list = new ArrayList<>();
        for (CategoryModel c:all){
            List<CommodityModel> sixByLm = service.findSixByLm(c.getId());
            if(sixByLm.size()>0){
                list.add(sixByLm);
            }else {
                list.add(new ArrayList<>());
            }
        }
        result.setData(list);
        return result;
    }
}
