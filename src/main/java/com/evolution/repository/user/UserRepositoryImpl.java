package com.evolution.repository.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.evolution.model.User;
import com.evolution.model.User_;
import com.evolution.repository.filter.UserFilter;
import com.evolution.repository.projection.Users;

public class UserRepositoryImpl implements UserRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Users> search(UserFilter filter, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
		Root<User> root = criteria.from(User.class);

		criteria.select(builder.construct(Users.class, 
				root.get(User_.ID), 
				root.get(User_.NAME), 
				root.get(User_.EMAIL),
				root.get(User_.TYPE_USER), 
				root.get(User_.STATUS)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(User_.NAME)));
		criteria.orderBy(orderList);

		Predicate[] predicates = createRestrictions(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<Users> query = manager.createQuery(criteria);
		addRestrictions(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, Users(filter));
	}

	private Long Users(UserFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<User> root = criteria.from(User.class);

		Predicate[] predicates = createRestrictions(filter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void addRestrictions(TypedQuery<Users> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalRegistrationPerPage = pageable.getPageSize();
		int firstPageRegister = currentPage * totalRegistrationPerPage;

		query.setFirstResult(firstPageRegister);
		query.setMaxResults(totalRegistrationPerPage);
	}

	private Predicate[] createRestrictions(UserFilter filter, CriteriaBuilder builder, Root<User> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filter.getId() != null) {
			predicates.add(builder.equal(root.get(User_.ID), filter.getId()));
		}

		if (filter.getStatus() != null) {
			predicates.add(builder.equal(root.get(User_.STATUS), filter.getStatus()));
		}

		if (filter.getTypeUser() != null) {
			predicates.add(builder.equal(root.get(User_.TYPE_USER), filter.getTypeUser()));
		}

		if (!StringUtils.isEmpty(filter.getName())) {
			predicates
					.add(builder.like(builder.lower(root.get(User_.NAME)), "%" + filter.getName().toLowerCase() + "%"));
		}

		if (!StringUtils.isEmpty(filter.getEmail())) {
			predicates.add(
					builder.like(builder.lower(root.get(User_.EMAIL)), "%" + filter.getEmail().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
