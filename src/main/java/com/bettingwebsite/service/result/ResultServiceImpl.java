package com.bettingwebsite.service.result;

import com.bettingwebsite.dao.ResultRepository;
import com.bettingwebsite.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ResultServiceImpl implements ResultService{
    private ResultRepository resultRepository;

    @Autowired
    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public Result findById(Long id) {
        Optional<Result> result = resultRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        else{
            throw new RuntimeException("Did not found result id: " + id);
        }
    }
}
