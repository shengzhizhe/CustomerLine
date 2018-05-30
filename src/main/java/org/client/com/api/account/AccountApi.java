package org.client.com.api.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.client.com.login.model.LoginModel;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.AccountService;
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

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
@RestController
@RequestMapping("/api/account")
public class AccountApi {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/sj/code/{token}", method = RequestMethod.GET)
    public ResponseResult<LoginModel> sjCode(@PathVariable("token") String token) {
        ResponseResult<TokenModel> result = tokenService.getByToken(token);
        if (result.isSuccess()) {
//            ResponseResult<LoginModel> result1 =
                    return accountService.getByAccount(result.getData().getAccount());
//            if (result1.isSuccess()) {
//                return new ResponseResult<>(true, "成功", result1.getData().getCoding(), 0);
//            } else {
//                return new ResponseResult<>(false, "帐户信息验证失败");
//            }
        } else {
            return new ResponseResult<>(false, "帐户信息验证失败");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(@RequestParam("json") String json) {
        ResponseResult<String> result = new ResponseResult<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginModel model = objectMapper.readValue(json, LoginModel.class);
            ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
            Validator validator = vf.getValidator();
            Set<ConstraintViolation<LoginModel>> set = validator.validate(model);
            for (ConstraintViolation<LoginModel> constraintViolation : set) {
                result.setSuccess(false);
                result.setMessage(constraintViolation.getMessage());
                return result;
            }
            //生成新的token
            long times = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
            TokenModel tokenModel = new TokenModel();
            tokenModel.setToken(GetUuid.getUUID());
            tokenModel.setIsUse("N");
            tokenModel.setEndTimes(times);
            tokenModel.setAccount(model.getUsername());
            tokenModel.setUuid(GetUuid.getUUID());
            ResponseResult<TokenModel> result1 = tokenService.add(tokenModel);
            if (!result1.isSuccess()) {
                result.setSuccess(false);
                result.setMessage("登录失败,请重新登录");
                return result;
            }
            ResponseResult<LoginModel> result2 = accountService.getByAccount(model.getUsername());
            if (result2.isSuccess()) {
                if (result2.getData().getTypes() == 1 || result2.getData().getTypes() == 12) {
                    if (model.getPassword().equals(result2.getData().getPassword())) {
                        result.setSuccess(true);
                        result.setMessage("登陆成功");
                        result.setData(result1.getData().getToken());
                        return result;
                    } else {
                        result.setSuccess(false);
                        result.setMessage("账号或密码错误");
                        return result;
                    }
                } else {
                    result.setSuccess(false);
                    result.setMessage("请升级为商家账号");
                    return result;
                }
            } else {
                result.setSuccess(false);
                result.setMessage("账号不存在");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("登录失败");
            return result;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestParam("pass") String pass, @RequestParam("token") String token) {
        ResponseResult<String> result = new ResponseResult<>();
        TokenModel tokenModel = new TokenModel();
        ResponseResult<TokenModel> byToken = tokenService.getByToken(token);
        LoginModel model = new LoginModel();
        model.setUsername(byToken.getData().getAccount());
        model.setPassword(pass);
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<LoginModel>> set = validator.validate(model);
        for (ConstraintViolation<LoginModel> constraintViolation : set) {
            result.setSuccess(false);
            result.setMessage(constraintViolation.getMessage());
            return result;
            //System.out.println(constraintViolation.getPropertyPath()+":"+constraintViolation.getMessage());
        }
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
                ResponseResult<LoginModel> upd = accountService.putPWD(model.getUsername(), model.getPassword());
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
