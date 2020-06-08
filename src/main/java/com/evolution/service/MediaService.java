package com.evolution.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.Media;
import com.evolution.repository.MediaRepository;

@Service
public class MediaService {

	@Autowired
	private MediaRepository repository;

	@Transactional
	public Media create(Media media) {
		Media save = repository.save(media);
		return save;
	}

	@Transactional
	public Media update(Long id, Media media) {
		Media save = findById(id);

		media.setUpdateDate(new Date());
		BeanUtils.copyProperties(media, save, "id");
		return repository.save(save);
	}

	@Transactional
	public void delete(Long id) {
		Media save = findById(id);
		repository.delete(save);
	}

	public Media findById(Long id) {
		Media save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return save;
	}
}
