package com.evolution.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.Publication;
import com.evolution.repository.PublicationRepository;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository repository;

	@Transactional
	public Publication create(Publication publication) {
		Publication save = repository.save(publication);
		return save;
	}

	@Transactional
	public Publication update(Long id, Publication publication) {
		Publication save = findById(id);

		publication.setUpdateDate(new Date());
		BeanUtils.copyProperties(publication, save, "id");
		return repository.save(save);
	}

	@Transactional
	public void delete(Long id) {
		Publication save = findById(id);
		repository.delete(save);
	}

	public Publication findById(Long id) {
		Publication save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return save;
	}
}
