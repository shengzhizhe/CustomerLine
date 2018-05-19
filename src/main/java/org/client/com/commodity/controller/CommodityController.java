package org.client.com.commodity.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.category.model.CategoryModel;
import org.client.com.category.service.CategoryService;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.commodity.service.CommodityService;
import org.client.com.login.model.LoginModel;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.AccountService;
import org.client.com.login.service.TokenService;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AccountService accountService;

    public CommodityController() {
    }

    @ApiOperation(
            value = "查询多个商品并且分页",
            response = ResponseResult.class,
            httpMethod = "GET")
    @RequestMapping(
            value = "/commodity/page/{lm}",
            method = RequestMethod.GET)
    public ResponseResult page(@PathVariable(value = "lm") String lm,ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token_str);
        String account = byToken.getData().getAccount();
        ResponseResult<LoginModel> byAccount = accountService.getByCoding(account);
        String username = byAccount.getData().getUsername();
        return service.findAllByPage( lm,username);
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
    public ResponseResult getByName(@PathVariable(value = "name") String name,ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token_str);
        String account = byToken.getData().getAccount();
        ResponseResult<LoginModel> byAccount = accountService.getByCoding(account);
        String username = byAccount.getData().getUsername();
        return service.getByName(name,username);
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
    public ResponseResult findSixByLm(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token_str = httpServletRequest.getHeader("token");
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token_str);
        String account = byToken.getData().getAccount();
        ResponseResult<LoginModel> byAccount = accountService.getByCoding(account);
        String username = byAccount.getData().getUsername();
        ResponseResult<List<List<CommodityModel>>> result = new ResponseResult<>();
        List<CategoryModel> all = categoryService.findAll();
        ArrayList<List<CommodityModel>> list = new ArrayList<>();
        for (CategoryModel c:all){
            List<CommodityModel> sixByLm = service.findSixByLm(c.getId(),username);
            if(sixByLm.size()>0){
                list.add(sixByLm);
            }else {
                list.add(new ArrayList<>());
            }
        }
        result.setData(list);
        return result;
    }

    /**
     * IO流读取图片 by:long
     * @return
     */
    @RequestMapping(value = "/IoReadImage/{imgName}", method = RequestMethod.GET)
    public void IoReadImage(@PathVariable String imgName,HttpServletRequest request,HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            //获取图片存放路径
            String imgPath = request.getSession().getServletContext().getRealPath("/") + imgName;
            ips = new FileInputStream(new File(imgPath));
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            ips.close();
        }
    }
}
