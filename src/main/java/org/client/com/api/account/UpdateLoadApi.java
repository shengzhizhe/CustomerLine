package org.client.com.api.account;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.commodity.service.CommodityService;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.TokenService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.uuidUtil.GetUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.logging.Logger;

@Api(value = "update", description = "上传")
@RestController
@RequestMapping(value = "/api/upload")
public class UpdateLoadApi {
    private static Logger logger = Logger.getLogger(UpdateLoadApi.class.toString());
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private TokenService tokenService;

    @ApiOperation(
            value = "上传",
            response = ResponseResult.class,
            httpMethod = "POST")
    @ResponseBody
    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST)
    public ResponseResult upload(HttpServletRequest request,
                                 @RequestParam("fileName")MultipartFile fileName) {
        String token = request.getParameter("token");
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
                try {
                    if (fileName.isEmpty()) {
                        result.setSuccess(false);
                        result.setData("}" + tokenModel.getToken());
                        result.setMessage("文件为空");
                        return result;
                    }
                    // 获取文件名
                    logger.info("上传的文件名为：" + fileName.getOriginalFilename());
                    // 获取文件的后缀名
//                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
//                    logger.info("文件的后缀名为：" + suffixName);

                    // 设置文件存储路径
                    String filePath = "D://img//";
                    String na = fileName.getOriginalFilename();
                    String path = filePath + GetUuid.getUUID()+"."+na.substring(na.lastIndexOf(".") + 1);

                    File dest = new File(path);
                    // 检测是否存在目录
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();// 新建文件夹
                    }
                    fileName.transferTo(dest);// 文件写入
                    result.setSuccess(true);
                    result.setData(path + "}" + tokenModel.getToken());
                    result.setMessage("上传成功");
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setSuccess(false);
                    result.setData("}" + tokenModel.getToken());
                    result.setMessage("上传失败");
                    return result;
                }
            } else {
                result.setSuccess(false);
                result.setData("}" + token);
                result.setMessage("系统繁忙，请稍后再试");
                return result;
            }
        } else
        {
            result.setSuccess(false);
            result.setMessage("令牌过期，请重新登陆");
            return result;
        }
    }
}

