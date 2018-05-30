package org.client.com.api.account;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.client.com.commodity.service.CommodityService;
import org.client.com.login.model.TokenModel;
import org.client.com.login.service.TokenService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.uuidUtil.GetUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.logging.Logger;

@Api(value = "update", description = "上传")
@RestController
@RequestMapping(value = "/api/upload")
public class UpdateLoadApi {
    private static Logger logger = Logger.getLogger(UpdateLoadApi.class.toString());

    @Value("${filesPath}")
    private String filesPath;

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
    public ResponseResult upload(@RequestParam("token") String token, @RequestParam("file") MultipartFile file) {
        ResponseResult<String> result = new ResponseResult<>();

        ResponseResult<TokenModel> byToken = tokenService.getByToken(token);
        if (!byToken.isSuccess()) {
            result.setSuccess(false);
            result.setMessage("帐户信息验证失败");
            return result;
        }

        try {
            if (file.isEmpty()) {
                result.setSuccess(false);
                result.setMessage("文件为空");
                return result;
            }
            // 获取文件名
            logger.info("上传的文件名为：" + file.getOriginalFilename());
            // 获取文件的后缀名
//                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
//                    logger.info("文件的后缀名为：" + suffixName);

            // 设置文件存储路径
            String na = file.getOriginalFilename();
            String path = filesPath + GetUuid.getUUID() + "." + na.substring(na.lastIndexOf(".") + 1);

            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            result.setSuccess(true);
            result.setData(dest.getName());
            result.setMessage("上传成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("上传失败");
            return result;
        }
    }
}

