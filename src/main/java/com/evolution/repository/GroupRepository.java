package com.evolution.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evolution.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
