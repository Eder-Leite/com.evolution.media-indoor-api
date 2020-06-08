package com.evolution.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evolution.email.EmailServiceImpl;
import com.evolution.email.Mail;
import com.evolution.model.Status;
import com.evolution.model.User;
import com.evolution.repository.UserRepository;
import com.evolution.repository.filter.AlterPassword;
import com.evolution.repository.filter.RessetPassword;
import com.evolution.repository.projection.Medias;
import com.evolution.service.UserService;

@RestController
@RequestMapping("/")
public class AuthResource {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserService service;

	@Autowired
	private EmailServiceImpl emailService;

	@GetMapping("userProfile")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<User> userProfile() {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User save = repository.findByEmail(email);
		return ResponseEntity.ok(save);
	}

	@GetMapping("showPublication")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER') and #oauth2.hasScope('read')")
	public List<Medias> showPublication() {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User save = repository.findByEmail(email);

		List<Medias> medias = new ArrayList<>();

		List<?> obj = repository.showPublication(save.getId());

		for (Object object : obj) {
			Medias media = new Medias(object);
			medias.add(media);
		}

		return medias;
	}

	@PostMapping("alterPassowrd")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<AlterPassword> alterPassowrd(@Valid @RequestBody AlterPassword alterPassword) {

		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User save = repository.findByEmail(email);

		if (service.autenticatePassword(alterPassword, save)) {
			save.setPassword(alterPassword.getNewPassword());
			service.update(save.getId(), save);

			alterPassword.setPassword("");
			alterPassword.setNewPassword("");
			return ResponseEntity.ok(alterPassword);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PostMapping("resetPassword")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<RessetPassword> resetPassword(@Valid @RequestBody RessetPassword ressetPassword,
			HttpServletResponse response) {

		User user = repository.findByEmailAndNameAndStatus(ressetPassword.getEmail().toUpperCase(),
				ressetPassword.getName(), Status.ACTIVE);

		if (user != null) {
			try {

				String password = service.generatorPassword();
				user.setPassword(password);

				service.update(user.getId(), user);

				Mail mail = new Mail();
				mail.setFrom("Media Indoor <nao-responda@evolutionsistemas.com.br>");
				mail.setTo(user.getName() + " <" + user.getEmail() + ">");
				mail.setSubject("Resset password");

				Map model = new HashMap();
				model.put("name", user.getName());
				model.put("email", user.getEmail());
				model.put("password", password);
				mail.setModel(model);

				emailService.sendSimpleMessage(mail, "email-template");

				// emailService.sendSimpleMessage(mail.getTo(),
				// mail.getSubject(), mail.getSubject());

			} catch (Exception e) {
				System.out.println(e.getMessage());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			return ResponseEntity.ok(ressetPassword);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
