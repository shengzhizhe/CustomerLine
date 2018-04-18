package org.client.com.api.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.TokenService;
import org.client.com.order.model.OrderModel;
import org.client.com.order.model.OrderSpModel;
import org.client.com.order.service.OrderService;
import org.client.com.order.service.OrderSpService;
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
@RequestMapping("/api/order")
public class OrderApi {
    @Autowired
    private OrderService service;
    @Autowired
    private OrderSpService orderSpService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/page/{pageNum}/{pageSize}/{type}",method = RequestMethod.GET)
    public ResponseResult page(
            @PathVariable("pageNum")int pageNum,
            @PathVariable("pageSize")int pageSize,
            @PathVariable("type")String type,
            @RequestParam("token")String token){
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
                    result.setMessage("令牌生成错误,请重新登录");
                    return result;
                }
                ResponseResult<Page<OrderModel>> page = service.page(pageNum, pageSize, byToken.getData().getAccount(),type);
                try {
                    if (page.isSuccess()) {
                        String jsonlist = objectMapper.writeValueAsString(page.getData());
                        result.setSuccess(true);
                        result.setData(jsonlist+tokenModel.getToken());
                        result.setMessage("成功");
                        return result;
                    }else {
                        result.setSuccess(false);
                        result.setData(tokenModel.getToken());
                        result.setMessage("未查询到相关数据");
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setSuccess(false);
                    result.setData(tokenModel.getToken());
                    result.setMessage("未查询到相关数据");
                    return result;
                }
            }else {
                result.setSuccess(false);
                result.setData(token);
                result.setMessage("系统繁忙，请稍后再试");
                return result;
            }
        }else {
            result.setSuccess(false);
            result.setMessage("令牌已过期，请重新登陆");
            return result;
        }
    }

    @RequestMapping(value = "/pageSp/{pageNum}/{pageSize}/{orid}",method = RequestMethod.GET)
    public ResponseResult pageSp(
            @PathVariable("pageNum")int pageNum,
            @PathVariable("pageSize")int pageSize,
            @PathVariable("orid") String orid,
            @RequestParam("token")String token){
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
                ResponseResult<Page<OrderSpModel>> page = orderSpService.page(pageNum, pageSize, orid);
                try {
                    if (page.isSuccess()) {
                        String jsonlist = objectMapper.writeValueAsString(page.getData());
                        result.setSuccess(true);
                        result.setData(jsonlist+tokenModel.getToken());
                        result.setMessage("成功");
                        return result;
                    }else {
                        result.setSuccess(false);
                        result.setData(tokenModel.getToken());
                        result.setMessage("未查询到相关数据");
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setSuccess(false);
                    result.setData(tokenModel.getToken());
                    result.setMessage("未查询到相关数据");
                    return result;
                }
            }else {
                result.setSuccess(false);
                result.setData(token);
                result.setMessage("系统繁忙，请稍后再试");
                return result;
            }
        }else {
            result.setSuccess(false);
            result.setMessage("令牌已过期，请重新登陆");
            return result;
        }
    }

//    @RequestMapping(value = "/order",method = RequestMethod.POST)
//    public ResponseResult order(@RequestParam("json")String json){
//        ResponseResult<String> result = new ResponseResult<>();
//        String s = json.substring(json.lastIndexOf("}") + 1);
//        String[] split = json.split(s);
//        ResponseResult<TokenModel> byToken = tokenService.getByToken(s);
//        TokenModel tokenModel = new TokenModel();
//        if (byToken.isSuccess() && "N".equals(byToken.getData().getIsUse())) {
//            ResponseResult<TokenModel> tokenModelResponseResult = tokenService.updateToken(s);
//            if (tokenModelResponseResult.isSuccess()) {
//                //生成新的token
//                long times = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
//                tokenModel.setToken(GetUuid.getUUID());
//                tokenModel.setIsUse("N");
//                tokenModel.setEndTimes(times);
//                tokenModel.setAccount(byToken.getData().getAccount());
//                tokenModel.setUuid(GetUuid.getUUID());
//                ResponseResult<TokenModel> result1 = tokenService.add(tokenModel);
//                if (!result1.isSuccess()) {
//                    result.setSuccess(false);
//                    result.setMessage("令牌生成错误,请重新登录");
//                    return result;
//                }
//
//                ObjectMapper objectMapper = new ObjectMapper();
//                try {
//                    OrderModel model = objectMapper.readValue(split[0], OrderModel.class);
//                    ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
//                    Validator validator = vf.getValidator();
//                    Set<ConstraintViolation<OrderModel>> set = validator.validate(model);
//                    for (ConstraintViolation<OrderModel> constraintViolation : set) {
//                        result.setSuccess(false);
//                        result.setMessage(constraintViolation.getMessage());
//                        result.setData(tokenModel.getToken());
//                        return result;
//                        //System.out.println(constraintViolation.getPropertyPath()+":"+constraintViolation.getMessage());
//                    }
//                    model.setBusid(byToken.getData().getAccount());
//                    ResponseResult update = service.add(model);
//                    if(update.isSuccess()){
//                        result.setSuccess(true);
//                        result.setMessage("修改成功");
//                        result.setData("}" + tokenModel.getToken());
//                        return result;
//                    }else {
//                        result.setSuccess(false);
//                        result.setMessage("修改失败");
//                        result.setData(tokenModel.getToken());
//                        return result;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    result.setSuccess(false);
//                    result.setMessage("修改失败");
//                    result.setData(tokenModel.getToken());
//                    return result;
//                }
//            }else {
//                result.setSuccess(false);
//                result.setData(s);
//                result.setMessage("系统繁忙，请稍后再试");
//                return result;
//            }
//        }else {
//            result.setSuccess(false);
//            result.setMessage("令牌已过期，请重新登陆");
//            return result;
//        }
//    }

    @RequestMapping(value = "/order/del/{uuid}",method = RequestMethod.DELETE)
    public ResponseResult del(@PathVariable String uuid,@RequestParam("token")String token){
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
                ResponseResult page = service.del(uuid);
                try {
                    if (page.isSuccess()) {
                        String jsonlist = objectMapper.writeValueAsString(page.getData());
                        result.setSuccess(true);
                        result.setData(jsonlist+tokenModel.getToken());
                        result.setMessage("成功");
                        return result;
                    }else {
                        result.setSuccess(false);
                        result.setData(tokenModel.getToken());
                        result.setMessage("未查询到相关数据");
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setSuccess(false);
                    result.setData(tokenModel.getToken());
                    result.setMessage("未查询到相关数据");
                    return result;
                }
            }else {
                result.setSuccess(false);
                result.setData(token);
                result.setMessage("系统繁忙，请稍后再试");
                return result;
            }
        }else {
            result.setSuccess(false);
            result.setMessage("令牌已过期，请重新登陆");
            return result;
        }
    }

}
