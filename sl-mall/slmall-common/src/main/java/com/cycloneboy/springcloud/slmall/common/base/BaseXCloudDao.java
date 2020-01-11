package com.cycloneboy.springcloud.slmall.common.base;


import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author sl
 */
// 自定义接口 不会创建接口的实例 必须加此注解
@NoRepositoryBean
public interface BaseXCloudDao<E, ID extends Serializable> extends JpaRepository<E, ID>,
    JpaSpecificationExecutor<E> {

}
