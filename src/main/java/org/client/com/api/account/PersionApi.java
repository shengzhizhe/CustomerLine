package org.client.com.api.account;

import org.client.com.login.model.LoginModel;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.TokenService;
import org.client.com.personaldata.service.PersonalService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.uuidUtil.GetUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RestController
@RequestMapping("/api/persion")
public class PersionApi {
    @Autowired
    private PersonalService service;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    public ResponseResult updateAddress(@RequestParam("address") String address, @RequestParam("token") String token) {
        ResponseResult<String> result = new ResponseResult<>();
        TokenModel tokenModel = new TokenModel();
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token);
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
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
                    result.setMessage("系统繁忙，请稍后再试");
                    return result;
                }
                ResponseResult<String> upd = service.updateAddress(byToken.getData().getAccount(),address);
                if (upd.isSuccess()) {
                    result.setSuccess(true);
                    result.setMessage("修改成功");
                    return result;
                } else {
                    result.setSuccess(false);
                    result.setData(tokenModel.getToken());
                    result.setMessage("修改失败");
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
            result.setMessage("令牌过期，请重新登陆");
            return result;
        }
    }

    @RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
    public ResponseResult updatePhone(@RequestParam("phone") String phone, @RequestParam("token") String token) {
        ResponseResult<String> result = new ResponseResult<>();
        TokenModel tokenModel = new TokenModel();
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token);
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
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
                    result.setMessage("系统繁忙，请稍后再试");
                    return result;
                }
                ResponseResult<String> upd = service.updatePhone(byToken.getData().getAccount(),phone);
                if (upd.isSuccess()) {
                    result.setSuccess(true);
                    result.setMessage("修改成功");
                    return result;
                } else {
                    result.setSuccess(false);
                    result.setData(tokenModel.getToken());
                    result.setMessage("修改失败");
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
            result.setMessage("令牌过期，请重新登陆");
            return result;
        }
    }
}
