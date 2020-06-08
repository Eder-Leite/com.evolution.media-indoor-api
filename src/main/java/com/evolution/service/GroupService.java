package com.evolution.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.Group;
import com.evolution.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository repository;

	@Transactional
	public Group create(Group group) {
		Group save = repository.save(group);
		return save;
	}

	@Transactional
	public Group update(Long id, Group group) {
		Group save = findById(id);

		group.setUpdateDate(new Date());
		BeanUtils.copyProperties(group, save, "id");
		return repository.save(save);
	}

	@Transactional
	public void delete(Long id) {
		Group save = findById(id);
		repository.delete(save);
	}

	public Group findById(Long id) {
		Group save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return save;
	}
}
