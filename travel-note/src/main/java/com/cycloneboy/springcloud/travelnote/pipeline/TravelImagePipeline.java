package com.cycloneboy.springcloud.travelnote.pipeline;


import com.cycloneboy.springcloud.common.entity.TravelImage;
import com.cycloneboy.springcloud.travelnote.dao.TravelImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TravelImagePipeline {

    @Autowired
    private TravelImageMapper travelImageMapper;

    public void save(TravelImage travelImage) {
        travelImageMapper.insert(travelImage);
    }

}

