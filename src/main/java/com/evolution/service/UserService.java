package com.evolution.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.User;
import com.evolution.repository.UserRepository;
import com.evolution.repository.filter.AlterPassword;

@Service
public class UserService {

	private String password;

	@Autowired
	private UserRepository repository;

	@Transactional
	public User create(User user) {
		User save = repository.save(user);
		return save;
	}

	@Transactional
	public User update(Long id, User user) {
		User save = findById(id);

		user.setUpdateDate(new Date());
		BeanUtils.copyProperties(user, save, "id");
		return repository.save(save);
	}

	@Transactional
	public void delete(Long id) {
		User save = findById(id);
		repository.delete(save);
	}

	public String generatorPassword() {
		String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C",
				"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };

		password = "";

		for (int x = 0; x < 8; x++) {
			int j = (int) (Math.random() * carct.length);
			password += carct[j];
		}
		return this.password;
	}

	public User findById(Long id) {
		User save = repository.findById(id).orElseThrow(() -> new ExceptionEntityService("Register not found!"));
		return save;
	}

	public boolean autenticatePassword(AlterPassword alterPassword, User user) {
		BCryptPasswordEncoder password = new BCryptPasswordEncoder();
		if (password.matches(alterPassword.getPassword(), user.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
