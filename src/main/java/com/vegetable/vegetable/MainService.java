package com.vegetable.vegetable;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class MainService {

    @PostConstruct
    public void init() {
       // TODO 데이터를 가져오는데 데이터가 없으면 파이썬을 실행시켜서 데이터를 가져와야함!

    }
}
