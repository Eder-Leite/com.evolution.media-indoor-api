package com.evolution.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evolution.model.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
