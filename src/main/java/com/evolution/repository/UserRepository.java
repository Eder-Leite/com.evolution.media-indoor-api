package com.evolution.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.evolution.model.Status;
import com.evolution.model.User;
import com.evolution.repository.user.UserRepositoryQuery;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryQuery {

	public User findByEmail(String email);

	public Optional<User> findByEmailAndStatus(String email, Status status);

	public User findByEmailAndNameAndStatus(String email, String name, Status status);

	@Query(value = "SELECT * FROM SHOW_PUBLICATION_USER A WHERE A.USERS = :USERS", nativeQuery = true)
	public List<?> showPublication(@Param("USERS") Long USERS);

}
