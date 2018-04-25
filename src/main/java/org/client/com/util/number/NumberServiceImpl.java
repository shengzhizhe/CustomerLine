package org.client.com.util.number;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumberServiceImpl {
    @Autowired
    private NumberMapper mapper;
    public int getNumber(){
        int number = mapper.getNumber();
        return number;
    }

    public int updateNumber(int number){
        int i = mapper.updateNumber(number);
        return i;
    }
}
