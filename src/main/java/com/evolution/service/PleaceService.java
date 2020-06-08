package com.evolution.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.Pleace;
import com.evolution.repository.PleaceRepository;

@Service
public class PleaceService {

	@Autowired
	private PleaceRepository repository;

	@Transactional
	public Pleace create(Pleace pleace) {
		Pleace save = repository.save(pleace);
		return save;
	}

	@Transactional
	public Pleace update(Long id, Pleace pleace) {
		Pleace save = findById(id);

		pleace.setUpdateDate(new Date());
		BeanUtils.copyProperties(pleace, save, "id");
		return repository.save(save);
	}

	@Transactional
	public void delete(Long id) {
		Pleace save = findById(id);
		repository.delete(save);
	}

	public Pleace findById(Long id) {
		Pleace save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return save;
	}
}
