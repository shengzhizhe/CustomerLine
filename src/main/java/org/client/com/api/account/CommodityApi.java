package org.client.com.api.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import org.client.com.commodity.model.CommodityModel;
import org.client.com.commodity.service.CommodityService;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.TokenService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.uuidUtil.GetUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RestController
@RequestMapping("/api/commodity")
public class CommodityApi {

    @Autowired
    private CommodityService service;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/commodity", method = RequestMethod.POST)
    public ResponseResult add(@RequestParam("json") String json) {
        ResponseResult<String> result = new ResponseResult<>();
        String s = json.substring(json.lastIndexOf("}") + 1);
        String[] split = json.split(s);
        TokenModel tokenModel = new TokenModel();
        ResponseResult<TokenModel> byToken = tokenService.getByToken(s);
        if (byToken.isSuccess() && "N".equals(byToken.getData().getIsUse())) {
            ResponseResult<TokenModel> tokenModelResponseResult = tokenService.updateToken(s);
            if (tokenModelResponseResult.isSuccess()) {
                //生成新的token
                long times = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
                tokenModel.setToken(GetUuid.getUUID());
                tokenModel.setIsUse("N");
                tokenModel.setEndTimes(times);
                tokenModel.setAccount(byToken.getData().getAccount());
                tokenModel.setUuid(GetUuid.getUUID());
                ResponseResult<TokenModel> result1 = tokenService.add(tokenModel);
                if (!result1.isSuccess()) {
                    result.setSuccess(false);
                    result.setData(s);
                    result.setMessage("系统繁忙，请稍后再试");
                    return result;
                }

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    CommodityModel model = objectMapper.readValue(split[0], CommodityModel.class);
                    ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
                    Validator validator = vf.getValidator();
                    Set<ConstraintViolation<CommodityModel>> set = validator.validate(model);
                    for (ConstraintViolation<CommodityModel> constraintViolation : set) {
                        result.setSuccess(false);
                        result.setMessage(constraintViolation.getMessage());
                        result.setData(result1.getData().getToken());
                        return result;
                        //System.out.println(constraintViolation.getPropertyPath()+":"+constraintViolation.getMessage());
                    }
                    model.setUuid(GetUuid.getUUID());
                    model.setBusid(byToken.getData().getAccount());
                    ResponseResult add = service.add(model);
                    if (add.isSuccess()) {
                        result.setSuccess(true);
                        result.setMessage("添加成功");
                        result.setData("}" + tokenModel.getToken());
                        return result;
                    } else {
                        result.setSuccess(false);
                        result.setMessage("添加失败");
                        result.setData(tokenModel.getToken());
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setSuccess(false);
                    result.setMessage("添加失败");
                    result.setData(tokenModel.getToken());
                    return result;
                }
            } else {
                result.setSuccess(false);
                result.setData(s);
                result.setMessage("系统繁忙，请稍后再试");
                return result;
            }
        } else {
            result.setSuccess(false);
            result.setMessage("令牌已过期，请重新登陆");
            return result;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestParam("json") String json) {
        ResponseResult<String> result = new ResponseResult<>();
        String s = json.substring(json.lastIndexOf("}") + 1);
        String[] split = json.split(s);
        ResponseResult<TokenModel> byToken = tokenService.getByToken(s);
        TokenModel tokenModel = new TokenModel();
        if (byToken.isSuccess() && "N".equals(byToken.getData().getIsUse())) {
            ResponseResult<TokenModel> tokenModelResponseResult = tokenService.updateToken(s);
            if (tokenModelResponseResult.isSuccess()) {
                //生成新的token
                long times = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
                tokenModel.setToken(GetUuid.getUUID());
                tokenModel.setIsUse("N");
                tokenModel.setEndTimes(times);
                tokenModel.setAccount(byToken.getData().getAccount());
                tokenModel.setUuid(GetUuid.getUUID());
                ResponseResult<TokenModel> result1 = tokenService.add(tokenModel);
                if (!result1.isSuccess()) {
                    result.setSuccess(false);
                    result.setMessage("令牌生成错误,请重新登录");
                    return result;
                }

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    CommodityModel model = objectMapper.readValue(split[0], CommodityModel.class);
                    ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
                    Validator validator = vf.getValidator();
                    Set<ConstraintViolation<CommodityModel>> set = validator.validate(model);
                    for (ConstraintViolation<CommodityModel> constraintViolation : set) {
                        result.setSuccess(false);
                        result.setMessage(constraintViolation.getMessage());
                        result.setData(tokenModel.getToken());
                        return result;
                        //System.out.println(constraintViolation.getPropertyPath()+":"+constraintViolation.getMessage());
                    }
                    model.setBusid(byToken.getData().getAccount());
                    ResponseResult update = service.update(model);
                    if (update.isSuccess()) {
                        result.setSuccess(true);
                        result.setMessage("修改成功");
                        result.setData("}" + tokenModel.getToken());
                        return result;
                    } else {
                        result.setSuccess(false);
                        result.setMessage("修改失败");
                        result.setData(tokenModel.getToken());
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setSuccess(false);
                    result.setMessage("修改失败");
                    result.setData(tokenModel.getToken());
                    return result;
                }
            } else {
                result.setSuccess(false);
                result.setData(s);
                result.setMessage("系统繁忙，请稍后再试");
                return result;
            }
        } else {
            result.setSuccess(false);
            result.setMessage("令牌已过期，请重新登陆");
            return result;
        }
    }

    @RequestMapping(value = "/commodity/page/{pageNow}/{pageSize}", method = RequestMethod.GET)
    public ResponseResult page(@PathVariable("pageNow") int pageNow, @PathVariable("pageSize") int pageSize, @RequestParam("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseResult<String> result = new ResponseResult<>();
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token);
        TokenModel tokenModel = new TokenModel();
        if (byToken.isSuccess() && "N".equals(byToken.getData().getIsUse())) {
            ResponseResult<TokenModel> tokenModelResponseResult = tokenService.updateToken(token);
            if (tokenModelResponseResult.isSuccess()) {
                //生成新的token
                long times = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
                tokenModel.setToken(GetUuid.getUUID());
                tokenModel.setIsUse("N");
                tokenModel.setEndTimes(times);
                tokenModel.setAccount(byToken.getData().getAccount());
                tokenModel.setUuid(GetUuid.getUUID());
                ResponseResult<TokenModel> result1 = tokenService.add(tokenModel);
                if (!result1.isSuccess()) {
                    result.setSuccess(false);
                    result.setData(token);
                    result.setMessage("令牌生成错误,请重新登录");
                    return result;
                }
                ResponseResult<Page<CommodityModel>> page = service.page(pageNow, pageSize, byToken.getData().getAccount());
                try {
                    if (page.isSuccess()) {
                        String jsonlist = objectMapper.writeValueAsString(page.getData());
                        result.setSuccess(true);
                        result.setData(jsonlist + tokenModel.getToken());
                        result.setMessage("成功");
                        return result;
                    } else {
                        result.setSuccess(false);
                        result.setData(tokenModel.getToken());
                        result.setMessage("未查询到相关数据");
                        return result;
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    result.setSuccess(false);
                    result.setData(tokenModel.getToken());
                    result.setMessage("未查询到相关数据");
                    return result;
                }
            } else {
                result.setSuccess(false);
                result.setData(token);
                result.setMessage("系统繁忙，请稍后再试");
                return result;
            }
        } else {
            result.setSuccess(false);
            result.setMessage("令牌已过期，请重新登陆");
            return result;
        }
    }

    @RequestMapping(value = "/del/{uuid}", method = RequestMethod.GET)
    public ResponseResult del(@PathVariable String uuid, @RequestParam("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseResult<String> result = new ResponseResult<>();
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token);
        if (byToken.isSuccess() && "N".equals(byToken.getData().getIsUse())) {
            ResponseResult<TokenModel> tokenModelResponseResult = tokenService.updateToken(token);
            if (tokenModelResponseResult.isSuccess()) {
                //生成新的token
                long times = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
                TokenModel tokenModel = new TokenModel();
                tokenModel.setToken(GetUuid.getUUID());
                tokenModel.setIsUse("N");
                tokenModel.setEndTimes(times);
                tokenModel.setAccount(byToken.getData().getAccount());
                tokenModel.setUuid(GetUuid.getUUID());
                ResponseResult<TokenModel> result1 = tokenService.add(tokenModel);
                if (!result1.isSuccess()) {
                    result.setSuccess(false);
                    result.setData(token);
                    result.setMessage("令牌生成错误,请重新登录");
                    return result;
                }
                ResponseResult del = service.del(uuid);
                if (del.isSuccess()) {
                    result.setSuccess(true);
                    result.setData("}" + tokenModel.getToken());
                    result.setMessage("删除成功");
                    return result;
                } else {
                    result.setSuccess(false);
                    result.setData(tokenModel.getToken());
                    result.setMessage("删除失败");
                    return result;
                }
            } else {
                result.setSuccess(false);
                result.setData(token);
                result.setMessage("系统繁忙，请稍后再试");
                return result;
            }
        } else {
            result.setSuccess(false);
            result.setMessage("令牌已过期，请重新登陆");
            return result;
        }
    }
}
