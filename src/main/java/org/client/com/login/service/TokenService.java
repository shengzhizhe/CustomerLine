package org.client.com.login.service;

import org.client.com.login.model.TokenModel;
import org.client.com.util.resultJson.ResponseResult;

public interface TokenService {

    ResponseResult<TokenModel> add(TokenModel model);
    ResponseResult<TokenModel> add2(TokenModel model);

    ResponseResult<TokenModel> updateToken(String token);
    ResponseResult<TokenModel> updateToken2(String token);

    ResponseResult<TokenModel> getByToken(String token);
    ResponseResult<TokenModel> getByToken2(String token);
}
