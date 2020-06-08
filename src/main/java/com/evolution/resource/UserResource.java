package com.evolution.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.evolution.model.User;
import com.evolution.repository.UserRepository;
import com.evolution.repository.filter.UserFilter;
import com.evolution.repository.projection.Users;
import com.evolution.service.ExceptionEntityService;
import com.evolution.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return ResponseEntity.ok(save);
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public List<User> list(HttpServletRequest request) {
		return repository.findAll(Sort.by("name"));
	}

	@GetMapping(params = "search")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public Page<Users> search(UserFilter filter, Pageable pageable) {
		return repository.search(filter, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<User> create(@Valid @RequestBody User user, HttpServletResponse response) {
		User save = service.create(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<User> atualizar(@PathVariable Long id, @RequestBody User user) {
		User save = service.update(id, user);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
