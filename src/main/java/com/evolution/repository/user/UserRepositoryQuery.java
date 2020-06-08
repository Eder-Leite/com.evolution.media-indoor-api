package com.evolution.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.evolution.repository.filter.UserFilter;
import com.evolution.repository.projection.Users;;

public interface UserRepositoryQuery {

	public Page<Users> search(UserFilter filter, Pageable pageable);

}
