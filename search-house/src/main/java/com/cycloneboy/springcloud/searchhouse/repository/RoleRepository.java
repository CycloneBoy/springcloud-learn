package com.cycloneboy.springcloud.searchhouse.repository;

import com.cycloneboy.springcloud.searchhouse.entity.Role;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * 角色数据DAO
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findRolesByUserId(Long userId);
}
