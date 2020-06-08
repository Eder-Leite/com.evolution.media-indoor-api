package com.evolution.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evolution.model.Group;
import com.evolution.repository.GroupRepository;
import com.evolution.service.ExceptionEntityService;
import com.evolution.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupResource {

	@Autowired
	private GroupRepository repository;

	@Autowired
	private GroupService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<Group> findById(@PathVariable Long id) {
		Group save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return ResponseEntity.ok(save);
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public List<Group> list(HttpServletRequest request) {
		return repository.findAll(Sort.by("description"));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Group> create(@Valid @RequestBody Group group, HttpServletResponse response) {
		Group save = service.create(group);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Group> atualizar(@PathVariable Long id, @RequestBody Group group) {
		Group save = service.update(id, group);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
