package com.cycloneboy.springcloud.goodskill.common.dynamicquery;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 * Create by  sl on 2019-12-10 21:01
 */
@Slf4j
@Repository
public class DynamicQueryImpl implements DynamicQuery {

  @PersistenceContext
  private EntityManager entityManager;

  public EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public void save(Object entity) {
    entityManager.persist(entity);
  }

  @Override
  public void update(Object entity) {
    entityManager.merge(entity);
  }

  @Override
  public <T> void delete(Class<T> entityClass, Object entityid) {
    delete(entityClass, new Object[]{entityid});
  }

  @Override
  public <T> void delete(Class<T> entityClass, Object[] entityids) {
    for (Object entityid : entityids) {
      entityManager.remove(entityManager.getReference(entityClass, entityid));
    }
  }

  private Query createNativeQuery(String nativeSql, Object... params) {
    Query query = entityManager.createNativeQuery(nativeSql);
    if (params != null && params.length > 0) {
      for (int i = 0; i < params.length; i++) {
        // 与Hiberante不同,jpa query从位置1开始
        query.setParameter(i + 1, params[i]);
      }
    }
    return query;
  }

  /**
   * 查询对象列表，返回List
   *
   * @param nativeSql
   * @param params
   * @return List<T>
   */
  @Override
  public <T> List<T> nativeQueryList(String nativeSql, Object... params) {
    Query query = createNativeQuery(nativeSql, params);
    query.unwrap(NativeQuery.class).setResultTransformer(Transformers.TO_LIST);
    return query.getResultList();
  }

  /**
   * 查询对象列表，返回List<Map<key,value>>
   *
   * @param nativeSql
   * @param params
   * @return List<T>
   */
  @Override
  public <T> List<T> nativeQueryListMap(String nativeSql, Object... params) {
    Query query = createNativeQuery(nativeSql, params);
    query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    return query.getResultList();
  }

  /**
   * 查询对象列表，返回List<组合对象>
   *
   * @param entityClass
   * @param nativeSql
   * @param params
   * @return List<T>
   */
  @Override
  public <T> List<T> nativeQueryListModel(Class<T> entityClass, String nativeSql,
      Object... params) {
    Query query = createNativeQuery(nativeSql, params);
    query.unwrap(NativeQuery.class).setResultTransformer(Transformers.TO_LIST);
    return query.getResultList();
  }

  /**
   * 执行nativeSql统计查询
   *
   * @param nativeSql
   * @param params    占位符参数(例如?1)绑定的参数值
   * @return 统计条数
   */
  @Override
  public Object nativeQueryObject(String nativeSql, Object... params) {
    return createNativeQuery(nativeSql, params).getSingleResult();
  }


  /**
   * 执行nativeSql统计查询
   *
   * @param nativeSql
   * @param params    占位符参数(例如?1)绑定的参数值
   * @return 统计条数
   */
  @Override
  public Object[] nativeQueryArray(String nativeSql, Object... params) {
    return (Object[]) createNativeQuery(nativeSql, params).getSingleResult();
  }

  /**
   * 执行nativeSql的update,delete操作
   *
   * @param nativeSql
   * @param params
   * @return
   */
  @Override
  public int nativeExecuteUpdate(String nativeSql, Object... params) {
    return createNativeQuery(nativeSql, params).executeUpdate();
  }
}
