package org.client.com.api.account;

import groovy.transform.AutoClone;
import org.client.com.category.model.CategoryModel;
import org.client.com.category.service.CategoryService;
import org.client.com.login.model.LoginModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {
    @Autowired
    private CategoryService service;

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public ResponseResult<String> findAll(){
        return service.findAll();
    }
}
