package org.client.com.api.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.client.com.login.model.LoginModel;
import org.client.com.login.service.AccountService;
import org.client.com.util.resultJson.ResponseResult;
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
@RequestMapping("/api/register")
public class RegisterApi {

    @Autowired
    private AccountService service;

    //商家注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseResult<String> register1(@RequestParam("json") String json) {
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
//                System.out.println(constraintViolation.getPropertyPath()+":"+constraintViolation.getMessage());
            }


            ResponseResult<LoginModel> result1 = service.getByAccount(model.getUsername());
            if (result1.isSuccess()){
                result.setSuccess(false);
                result.setMessage("账号重复");
                return result;
            }else{
                model.setTypes(1);
                ResponseResult<LoginModel> add = service.add(model);
                if(add.isSuccess()){
                    result.setSuccess(true);
                    result.setMessage("注册成功");
                    return result;
                }else{
                    result.setSuccess(false);
                    result.setMessage("注册失败");
                    return result;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("注册失败");
            return result;
        }
    }
}
