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

import com.evolution.model.Publication;
import com.evolution.repository.PublicationRepository;
import com.evolution.service.ExceptionEntityService;
import com.evolution.service.PublicationService;

@RestController
@RequestMapping("/publications")
public class PublicationResource {

	@Autowired
	private PublicationRepository repository;

	@Autowired
	private PublicationService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<Publication> findById(@PathVariable Long id) {
		Publication save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return ResponseEntity.ok(save);
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public List<Publication> list(HttpServletRequest request) {
		return repository.findAll(Sort.by("id"));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Publication> create(@Valid @RequestBody Publication publication,
			HttpServletResponse response) {
		Publication save = service.create(publication);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Publication> atualizar(@PathVariable Long id, @RequestBody Publication publication) {
		Publication save = service.update(id, publication);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
