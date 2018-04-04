package org.client.com.personaldata.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.personaldata.model.PersonalModel;
import org.client.com.personaldata.service.PersonalService;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author ld
 * @name 个人资料
 * @table
 * @remarks
 */
@Api(value = "personalData", description = "个人资料")
@RestController
@RequestMapping("/personalData")
public class PersonalDataController {

    @Autowired
    private PersonalService personalService;

    @ApiOperation(value = "获取个人资料",
            response = ResponseResult.class,
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/personalData/account/{account}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<PersonalModel> getByAccount(@PathVariable("account") String account) {
        return personalService.getByAccount(account);
    }

    @ApiOperation(value = "修改个人资料",
            response = ResponseResult.class,
            httpMethod = "PUT",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/personalData", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<PersonalModel> update(@RequestBody PersonalModel model) {
        return personalService.update(model);
    }
}
