package org.client.com.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.Account;

public class JsonUtil<T> {
    public String jsonToString(String json,String t){
        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode node = objectMapper.readTree(json);// 这里的JsonNode和XML里面的Node很像
//        System.out.println(“readValueFromJson>>>” + node.get(“voName”).toString());// 获取voName
//        try {
//            JsonNode node = objectMapper.readTree(json);// 这里的JsonNode和XML里面的Node很像
//            String string1 = node.get(t).toString();// 获取voName
//            return string1;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return "";
    }

}
