package com.cycloneboy.springcloud.mafengwo.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cycloneboy.springcloud.common.common.HttpExceptionEnum;
import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.common.domain.PageResponse;
import com.cycloneboy.springcloud.common.domain.dto.mafengwo.TravelNoteRequest;
import com.cycloneboy.springcloud.mafengwo.entity.TravelNote;
import com.cycloneboy.springcloud.mafengwo.service.TravelNoteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Create by  sl on 2019-08-10 23:14
 */
@Slf4j
@RestController
@RequestMapping("/travelnote")
public class TravelNoteController {

    @Autowired
    private TravelNoteService travelNoteService;

    /**
     *  根据ID查询游记
     * @return BaseResponse
     */
    @GetMapping
    public BaseResponse queryTravelNoteList(@Valid TravelNoteRequest request) {
        Page<TravelNote> page = new Page<>(request.getPageNumber(),request.getPageSize());

        TravelNote travelNote = new TravelNote();
        BeanUtils.copyProperties(request,travelNote);
        log.info(JSON.toJSONString(travelNote));

        Wrapper<TravelNote> wrapper = new QueryWrapper<>(travelNote);
        IPage<TravelNote> noteIPage = travelNoteService.page(page, wrapper);
        return new PageResponse(noteIPage.getRecords(),noteIPage.getTotal());

    }

    /**
     *  根据ID查询游记
     * @param id
     * @return BaseResponse
     */
    @GetMapping("/{id}")
    public BaseResponse queryTravelNote(@PathVariable Integer id){
        return new BaseResponse(travelNoteService.getById(id));
    }


    /**
     * 保存游记
     * @param travelNote
     * @return BaseResponse
     */
    @PostMapping
    public BaseResponse addTravelNote(@Valid @RequestBody TravelNote travelNote) {
        boolean success = travelNoteService.save(travelNote);
        if(!success){
            return  new BaseResponse(HttpExceptionEnum.FAILED);
        }
        return new BaseResponse(travelNote);
    }

    /**
     *  删除游记
     * @param id
     * @return BaseResponse
     */
    @DeleteMapping("/{id}")
    public BaseResponse deleteTravelNoteById(@PathVariable Integer id) {
        return new BaseResponse(travelNoteService.removeById(id));
    }

    /**
     * 修改游记
     * @param travelNote
     * @return
     */
    @PutMapping
    public BaseResponse update(@Valid @RequestBody TravelNote travelNote) {
        return new BaseResponse(travelNoteService.updateById(travelNote));
    }


    @GetMapping("/")
    public BaseResponse startCrawlHotNoteList(@RequestParam(name = "year") int year,
                                        @RequestParam(name = "month") int month,
                                        @RequestParam(name = "day") int day) {

        QueryWrapper<TravelNote> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TravelNote::getYear, year);

        if (month > 0) {
            queryWrapper.lambda().eq(TravelNote::getMonth, month);
        }

        if (day > 0) {
            queryWrapper.lambda().eq(TravelNote::getDay, day);
        }

        List<TravelNote> travelNoteList = travelNoteService.list(queryWrapper);
        return new PageResponse(travelNoteList);
    }



}
