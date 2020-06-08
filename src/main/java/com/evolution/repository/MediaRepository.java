package com.evolution.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evolution.model.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

}
