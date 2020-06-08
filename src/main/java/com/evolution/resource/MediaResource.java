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

import com.evolution.model.Media;
import com.evolution.repository.MediaRepository;
import com.evolution.service.ExceptionEntityService;
import com.evolution.service.MediaService;

@RestController
@RequestMapping("/medias")
public class MediaResource {

	@Autowired
	private MediaRepository repository;

	@Autowired
	private MediaService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<Media> findById(@PathVariable Long id) {
		Media save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return ResponseEntity.ok(save);
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public List<Media> list(HttpServletRequest request) {
		return repository.findAll(Sort.by("title"));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Media> create(@Valid @RequestBody Media media, HttpServletResponse response) {
		Media save = service.create(media);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Media> atualizar(@PathVariable Long id, @RequestBody Media media) {
		Media save = service.update(id, media);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
