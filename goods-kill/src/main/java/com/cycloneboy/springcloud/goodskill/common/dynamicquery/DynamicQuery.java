package com.cycloneboy.springcloud.goodskill.common.dynamicquery;

import java.util.List;

/**
 * 扩展SpringDataJpa, 支持动态jpql/nativesql查询并支持分页查询
 * <p>
 * <p>
 * Create by  sl on 2019-12-10 20:53
 */
public interface DynamicQuery {

  void save(Object entity);

  void update(Object entity);

  <T> void delete(Class<T> entityClass, Object entityid);

  <T> void delete(Class<T> entityClass, Object[] entityids);

  /**
   * 查询对象列表，返回List
   *
   * @param nativeSql
   * @param params
   * @param <T>
   * @return List<T>
   */
  <T> List<T> nativeQueryList(String nativeSql, Object... params);

  /**
   * 查询对象列表，返回List<Map<key,value>>
   *
   * @param nativeSql
   * @param params
   * @param <T>
   * @return List<T>
   */
  <T> List<T> nativeQueryListMap(String nativeSql, Object... params);

  /**
   * 查询对象列表，返回List<组合对象>
   *
   * @param entityClass
   * @param nativeSql
   * @param params
   * @param <T>
   * @return List<T>
   */
  <T> List<T> nativeQueryListModel(Class<T> entityClass, String nativeSql, Object... params);

  /**
   * 执行nativeSql统计查询
   *
   * @param nativeSql
   * @param params    占位符参数(例如?1)绑定的参数值
   * @return 统计条数
   */
  Object nativeQueryObject(String nativeSql, Object... params);

  /**
   * 执行nativeSql统计查询
   *
   * @param nativeSql
   * @param params    占位符参数(例如?1)绑定的参数值
   * @return 统计条数
   */
  Object[] nativeQueryArray(String nativeSql, Object... params);

  /**
   * 执行nativeSql的update,delete操作
   *
   * @param nativeSql
   * @param params
   * @return
   */
  int nativeExecuteUpdate(String nativeSql, Object... params);
}
