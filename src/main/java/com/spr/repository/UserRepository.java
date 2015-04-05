package com.spr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spr.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Table;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where lower(u.name) like(lower(:name))")
    public List<User> findByName(@Param("name") String name);

    @Query("select u from User u where lower(u.name) like(lower(:name)) order by u.name")
    public List<User> findAll(@Param("name") String name, Pageable pageable);

    @Query("select count(u.id) from User u where lower(u.name) like(lower(:name))")
    public Long getCountAllRows(@Param("name") String name);

}
