package org.client.com.category.service;

import org.client.com.category.model.CategoryModel;
import org.client.com.util.resultJson.ResponseResult;

import java.util.List;

public interface CategoryService {
    ResponseResult<String>  findAll();
    List<CategoryModel>  findAll2();
}
