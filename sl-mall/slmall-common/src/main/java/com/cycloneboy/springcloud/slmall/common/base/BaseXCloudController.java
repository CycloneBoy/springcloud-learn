package com.cycloneboy.springcloud.slmall.common.base;

import com.cycloneboy.springcloud.common.common.HttpExceptionEnum;
import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.slmall.common.utils.PageUtil;
import com.cycloneboy.springcloud.slmall.common.vo.PageVo;
import io.swagger.annotations.ApiOperation;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sl
 */
public abstract class BaseXCloudController<E, ID extends Serializable> {

    /**
     * 获取service
     *
     * @return
     */
    @Autowired
    public abstract BaseXCloudService<E, ID> getService();

    @GetMapping(value = "/get/{id}")
    @ResponseBody
    @ApiOperation(value = "通过id获取")
    public BaseResponse get(@PathVariable ID id) {

        E entity = getService().get(id).orElse(null);
        return new BaseResponse(entity);
    }

    @GetMapping(value = "/getAll")
    @ResponseBody
    @ApiOperation(value = "获取全部数据")
    public BaseResponse getAll() {

        List<E> list = getService().getAll();
        return new BaseResponse(list);
    }

    @GetMapping(value = "/getByPage")
    @ResponseBody
    @ApiOperation(value = "分页获取")
    public BaseResponse getByPage(@RequestBody PageVo page) {

        Page<E> data = getService().findAll(PageUtil.initPage(page));
        return new BaseResponse(data);
    }

    @PostMapping(value = "/save")
    @ResponseBody
    @ApiOperation(value = "保存数据")
    public BaseResponse save(@RequestBody E entity) {

        E e = getService().save(entity);
        return new BaseResponse(e);
    }

    @PutMapping(value = "/update")
    @ResponseBody
    @ApiOperation(value = "更新数据")
    public BaseResponse update(@RequestBody E entity) {

        E e = getService().update(entity);
        return new BaseResponse(e);
    }

    @DeleteMapping(value = "/delByIds")
    @ResponseBody
    @ApiOperation(value = "批量通过id删除")
    public BaseResponse delAllByIds(@RequestParam ID[] ids) {

        for (ID id : ids) {
            getService().delete(id);
        }
        return new BaseResponse(HttpExceptionEnum.SUCCESS, "批量通过id删除数据成功");
    }
}
