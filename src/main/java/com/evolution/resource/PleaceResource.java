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

import com.evolution.model.Pleace;
import com.evolution.repository.PleaceRepository;
import com.evolution.service.ExceptionEntityService;
import com.evolution.service.PleaceService;

@RestController
@RequestMapping("/pleaces")
public class PleaceResource {

	@Autowired
	private PleaceRepository repository;

	@Autowired
	private PleaceService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<Pleace> findById(@PathVariable Long id) {
		Pleace save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return ResponseEntity.ok(save);
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public List<Pleace> list(HttpServletRequest request) {
		return repository.findAll(Sort.by("id"));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Pleace> create(@Valid @RequestBody Pleace pleace, HttpServletResponse response) {
		Pleace save = service.create(pleace);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Pleace> atualizar(@PathVariable Long id, @RequestBody Pleace pleace) {
		Pleace save = service.update(id, pleace);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
