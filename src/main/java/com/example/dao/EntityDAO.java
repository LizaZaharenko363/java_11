package com.example.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.entity.MyEntity;
import java.util.List;

@Stateless
public class EntityDAO {

    @PersistenceContext(unitName = "MySqlPU")
    private EntityManager em;

    public MyEntity create(MyEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        em.persist(entity);
        em.flush();
        return entity;
    }
    public MyEntity read(Long id) {
        return em.find(MyEntity.class, id);
    }

    public MyEntity update(MyEntity entity) {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("Entity and its ID cannot be null");
        }
        MyEntity existingEntity = em.find(MyEntity.class, entity.getId());
        if (existingEntity == null) {
            throw new jakarta.persistence.EntityNotFoundException("Entity not found with ID: " + entity.getId());
        }

        existingEntity.setName(entity.getName());
        return em.merge(existingEntity);
    }
    public void delete(Long id) {
        MyEntity entity = read(id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public List<MyEntity> findByName(String name) {
        return em.createQuery("SELECT e FROM MyEntity e WHERE e.name = :name", MyEntity.class)
                .setParameter("name", name)
                .getResultList();
    }
}
