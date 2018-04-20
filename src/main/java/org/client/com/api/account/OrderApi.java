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

import java.util.List;

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

    @RequestMapping(value = "/order/findByType",method = RequestMethod.GET)
    public ResponseResult findByType(@RequestParam("token")String token){
        ResponseResult<String> result = new ResponseResult<>();
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token);
        List<OrderModel> byType = service.findByType(byToken.getData().getAccount());
            if(byType.size()>0){
                ResponseResult update = service.update(byToken.getData().getAccount());
                if(update.isSuccess()){
                    result.setSuccess(true);
                }else {
                    result.setSuccess(false);
                }
            }else {
                result.setSuccess(false);
            }
        return result;
    }

    @RequestMapping(value = "/order/UpdateOrDel",method = RequestMethod.PUT)
    public ResponseResult UpdateOrDel(@RequestParam("uuid") String uuid,@RequestParam("type") int type,@RequestParam("token") String token){
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
                ResponseResult page = service.updateOrDel(uuid,type);
                try {
                    if (page.isSuccess()) {
                        result.setSuccess(true);
                        result.setData("}"+tokenModel.getToken());
                        result.setMessage("成功");
                        return result;
                    }else {
                        result.setSuccess(false);
                        result.setData(tokenModel.getToken());
                        result.setMessage("失败请稍后再试");
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
