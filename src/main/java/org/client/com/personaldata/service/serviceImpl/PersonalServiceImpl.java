package org.client.com.personaldata.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.client.com.personaldata.mapper.PersonalMapper;
import org.client.com.personaldata.model.PersonalModel;
import org.client.com.personaldata.service.PersonalService;
import org.client.com.util.resultJson.ResponseResult;
import org.client.com.util.sl4j.Sl4jToString;
import org.client.com.util.uuidUtil.GetUuid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * logger.info(Sl4jToString.info(1,
 * serviceName,
 * Thread.currentThread().getStackTrace()[1].getMethodName(),
 * model.toString(),
 * 200,
 * null));
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PersonalServiceImpl implements PersonalService {

    private static Logger logger = LoggerFactory.getLogger(PersonalServiceImpl.class);

    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    private PersonalMapper mapper;

    @Override
    public ResponseResult<PersonalModel> save(PersonalModel model) {
        ResponseResult<PersonalModel> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.toString(),
                result.getCode(),
                null));
        if (model.getNickname() != null && !model.getNickname().isEmpty()) {
            PersonalModel model1 = mapper.getByNickname(model.getNickname());
            if (model1 != null) {
                result.setData(null);
                result.setSuccess(false);
                result.setMessage("资料重复");
                logger.info(Sl4jToString.info(2,
                        serviceName,
                        Thread.currentThread().getStackTrace()[1].getMethodName(),
                        model.toString(),
                        result.getCode(),
                        null));
                return result;
            }
        }
        model.setUuid(GetUuid.getUUID());
        int i = mapper.save(model);
        switch (i) {
            case 1:
                result.setSuccess(true);
                result.setMessage("数据更新成功");
                break;
            default:
                result.setSuccess(false);
                result.setMessage("数据更新失败");
        }
        result.setData(null);
        logger.info(Sl4jToString.info(2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.toString(),
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<PersonalModel> delete(String id) {
        ResponseResult<PersonalModel> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                id,
                result.getCode(),
                null));
        int i = mapper.delete(id);
        switch (i) {
            case 1:
                result.setSuccess(true);
                result.setMessage("成功");
                break;
            default:
                result.setSuccess(false);
                result.setMessage("错误");
        }
        logger.info(Sl4jToString.info(2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                id,
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<PersonalModel> update(PersonalModel model) {
        ResponseResult<PersonalModel> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.toString(),
                result.getCode(),
                null));
        int i = mapper.update(model);
        switch (i) {
            case 1:
                result.setSuccess(true);
                result.setMessage("数据更新成功");
                break;
            default:
                result.setSuccess(false);
                result.setMessage("数据更新错误");
        }
        logger.info(Sl4jToString.info(2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                model.toString(),
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<Page<PersonalModel>> findAll(int now, int size) {
        ResponseResult<Page<PersonalModel>> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                null,
                result.getCode(),
                null));
        PageHelper.startPage(now, size);
        Page<PersonalModel> page = mapper.findAll();
        result.setSuccess(true);
        result.setData(page);
        logger.info(Sl4jToString.info(2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                null,
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<PersonalModel> getById(String id) {
        ResponseResult<PersonalModel> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                id,
                result.getCode(),
                null));
        PersonalModel model = mapper.getById(id);
        if (model != null) {
            result.setSuccess(true);
            result.setData(model);
        } else {
            result.setSuccess(false);
        }
        logger.info(Sl4jToString.info(2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                id,
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<PersonalModel> getByAccount(String account) {
        ResponseResult<PersonalModel> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        PersonalModel model = mapper.getByAccount(account);
        if (model != null) {
            result.setSuccess(true);
            result.setData(model);
            result.setMessage(null);
        } else {
            result.setSuccess(false);
            result.setData(null);
            result.setMessage("未获取到数据");
        }
        logger.info(Sl4jToString.info(2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        return result;
    }

    @Override
    public ResponseResult<String> updateAddress(String account, String address) {
        ResponseResult<String> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        int i = mapper.updateAddress(account,address);
        if (i == 1) {
            result.setSuccess(true);
            result.setMessage(null);
        } else {
            result.setSuccess(false);
            result.setMessage("未获取到数据");
        }
        logger.info(Sl4jToString.info(2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        return result;
    }
    @Override
    public ResponseResult<String> updatePhone(String account, String phone) {
        ResponseResult<String> result = new ResponseResult<>();
        logger.info(Sl4jToString.info(1,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        int i = mapper.updatePhone(account,phone);
        if (i == 1) {
            result.setSuccess(true);
            result.setMessage(null);
        } else {
            result.setSuccess(false);
            result.setMessage("未获取到数据");
        }
        logger.info(Sl4jToString.info(2,
                serviceName,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                account,
                result.getCode(),
                null));
        return result;
    }

}
