package com.zeroturnaround.rebelanswers.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class DaoTools {

  @PersistenceContext
  private EntityManager entityManager;

  public DaoTools() {
  }

  public DaoTools(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public <T> T findById(final Class<T> entityClass, final Object id) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == id) throw new IllegalArgumentException("id can't be null");

    return entityManager.find(entityClass, id);
  }

  @SuppressWarnings("unchecked")
  private <T> List<T> castResultList(List<?> results) {
    return (List<T>) results;
  }

  public <T> List<T> findByAttribute(final Class<T> entityClass, final String attributeName, final Object attributeValue) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == attributeName) throw new IllegalArgumentException("attributeName can't be null");
    if (null == attributeValue) throw new IllegalArgumentException("attributeValue can't be null");

    return castResultList(entityManager.createQuery(
        "select e from " + entityClass.getSimpleName() + " e where e." + attributeName + " = ?1")
        .setParameter(1, attributeValue).getResultList());
  }

  public <T> List<T> findByAttributes(final Class<T> entityClass, final String orderByAttributeName,
                                      final SortOrder sortOrder, final Object... attributeNamesValues) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == orderByAttributeName) throw new IllegalArgumentException("orderByAttributeName can't be null");
    if (null == attributeNamesValues) throw new IllegalArgumentException("attributeNamesValues can't be null");
    if (attributeNamesValues.length == 0) throw new IllegalArgumentException("attributeNamesValues can't be empty");
    if (attributeNamesValues.length % 2 != 0) throw new IllegalArgumentException("attributeNamesValues should be a series of name value pairs");

    StringBuilder attributes = new StringBuilder();
    for (int i = 0; i < attributeNamesValues.length; i += 2) {
      if (attributes.length() > 0) {
        attributes.append(" and ");
      }
      attributes.append("e.");
      attributes.append(attributeNamesValues[i]);
      attributes.append(" = ?");
      attributes.append(i / 2 + 1);
    }

    Query query = entityManager.createQuery(
        "select e from " + entityClass.getSimpleName() + " e where " + attributes + " order by e." + orderByAttributeName + " " + sortOrder.name());
    for (int i = 1; i < attributeNamesValues.length; i += 2) {
      query.setParameter(i / 2 + 1, attributeNamesValues[i]);
    }
    return castResultList(query.getResultList());
  }

  public <T> List<T> findByPossibleAttributes(final Class<T> entityClass, final String[] attributeNames, final Object attributeValue) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == attributeNames) throw new IllegalArgumentException("attributeNames can't be null");
    if (attributeNames.length == 0) throw new IllegalArgumentException("attributeNames can't be empty");
    if (null == attributeValue) throw new IllegalArgumentException("attributeValue can't be null");

    StringBuilder attributes = new StringBuilder();
    for (String attributeName : attributeNames) {
      if (attributes.length() > 0) {
        attributes.append(" or ");
      }
      attributes.append("e.");
      attributes.append(attributeName);
      attributes.append(" = ?1");
    }
    return castResultList(entityManager.createQuery(
        "select e from " + entityClass.getSimpleName() + " e where " + attributes)
        .setParameter(1, attributeValue).getResultList());
  }

  public <T> List<T> getAllEntities(final Class<T> entityClass, final String orderByAttributeName,
                                    final SortOrder sortOrder, final Integer offset, final Integer limit) {
    return getFilteredEntities(entityClass, orderByAttributeName, sortOrder, "", offset, limit);
  }

  public <T> List<T> getFilteredEntities(final Class<T> entityClass, final String orderByAttributeName,
                                         final SortOrder sortOrder, final String filter, final Integer offset, final Integer limit) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == orderByAttributeName) throw new IllegalArgumentException("orderByAttributeName can't be null");
    if (null == filter) throw new IllegalArgumentException("filter can't be null");

    Query query = entityManager.createQuery(
        "select e from " + entityClass.getSimpleName() + " e " + filter + " order by e." + orderByAttributeName + " " + sortOrder.name());
    if (offset != null) {
      query.setFirstResult(offset);
    }
    if (limit != null) {
      query.setMaxResults(limit);
    }
    return castResultList(query.getResultList());
  }

  public <T> List<T> searchByAttribute(final Class<T> entityClass, final String orderByAttributeName, final SortOrder sortOrder, final String attributeName, final Object attributeValue,
                                       final Integer offset, final Integer limit) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == attributeName) throw new IllegalArgumentException("attributeName can't be null");
    if (null == attributeValue) throw new IllegalArgumentException("attributeValue can't be null");
    if (null == orderByAttributeName) throw new IllegalArgumentException("orderByAttributeName can't be null");

    Query query = entityManager.createQuery(
        "select e from " + entityClass.getSimpleName() + " e where e." + attributeName +
            " LIKE ?1 order by e." + orderByAttributeName + " " + sortOrder.name());
    if (offset != null) {
      query.setFirstResult(offset);
    }
    if (limit != null) {
      query.setMaxResults(limit);
    }
    return castResultList(query.setParameter(1, "%" + String.valueOf(attributeValue).replace("%", "") + "%").getResultList());
  }

  public long countSearchByAttribute(final Class<?> entityClass, final String attributeName, final Object attributeValue) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == attributeName) throw new IllegalArgumentException("attributeName can't be null");
    if (null == attributeValue) throw new IllegalArgumentException("attributeValue can't be null");

    Query query = entityManager.createQuery(
        "select count(e) from " + entityClass.getSimpleName() + " e where e." + attributeName + " LIKE ?1");
    return (Long) query.setParameter(1, "%" + String.valueOf(attributeValue).replace("%", "") + "%").getSingleResult();
  }

  public <T> T persist(final Class<T> entityClass, final T entity) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == entity) throw new IllegalArgumentException("entity can't be null");

    entityManager.persist(entity);
    return entity;
  }

  public <T> T merge(final Class<T> entityClass, final T entity) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == entity) throw new IllegalArgumentException("entity can't be null");

    return entityManager.merge(entity);
  }

  public long countEntities(final Class<?> entityClass) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");

    return (Long) entityManager.createQuery("select count(entity) from " + entityClass.getSimpleName() + " entity").getSingleResult();
  }

  public long countFilteredEntities(final Class<?> entityClass, final String filter) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == filter) throw new IllegalArgumentException("filter can't be null");

    return (Long) entityManager.createQuery(
        "select count(e) from " + entityClass.getSimpleName() + " e " + filter).getSingleResult();
  }

  public boolean delete(final Object entity) {
    if (null == entity) throw new IllegalArgumentException("entity can't be null");

    entityManager.remove(entity);
    return true;
  }

  public <T> boolean deleteById(final Class<T> entityClass, final Object id) {
    if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
    if (null == id) throw new IllegalArgumentException("id can't be null");

    return delete(findById(entityClass, id));
  }

  public enum SortOrder {
    ASC, DESC
  }
}
