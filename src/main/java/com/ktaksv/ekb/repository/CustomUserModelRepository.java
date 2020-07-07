package com.ktaksv.ekb.repository;

import com.ktaksv.ekb.model.RoleModel;
import com.ktaksv.ekb.model.UserModel;
import com.ktaksv.ekb.model.dto.SortDirection;
import com.ktaksv.ekb.model.dto.UserModelFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserModelRepository {

    private final EntityManager em;

    public Page<UserModel> findAllFiltered(Pageable pageable, UserModelFilterDto filter) {

        UserModelFilterDto dto = Optional.ofNullable(filter).orElse(new UserModelFilterDto());

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<UserModel> cr = cb.createQuery(UserModel.class).orderBy();
        Root<UserModel> root = cr.from(UserModel.class);
        Predicate[] predicatesWrit = this.getPredicates(cb, root, dto);
        cr.select(root).where(predicatesWrit);

        this.getSort(dto, cb, root, cr);

        List<UserModel> list = em.createQuery(cr)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<UserModel> from = countQuery.from(UserModel.class);
        Predicate[] predicatesCount = this.getPredicates(cb, from, dto);
        countQuery.select(cb.count(root));
        countQuery.where(predicatesCount).distinct(true);
        Long count = em.createQuery(countQuery)
                .getSingleResult();
        return new PageImpl<>(list, pageable, count);
    }

    private Predicate[] getPredicates(CriteriaBuilder cb, Root<UserModel> root, UserModelFilterDto filter) {
        LocalDateTime endOfDate = Optional.ofNullable(filter.getStop())
                .orElse(LocalDate.now().plus(100L, ChronoUnit.YEARS)).atTime(LocalTime.MAX);
        LocalDateTime startOfDate = Optional.ofNullable(filter.getStart())
                .orElse(LocalDate.now().minus(100L, ChronoUnit.YEARS)).atTime(LocalTime.MIN);

        List<Predicate> result = new ArrayList<>();

        result.add(this.getDateTimePredicate(startOfDate, endOfDate, cb, root));

        Optional.ofNullable(filter.getDepartment())
                .ifPresent(value -> result.add(this.getDepartmentPredicate(value, cb, root)));

        Optional.ofNullable(filter.getFio())
                .ifPresent(value -> result.add(this.getFioPredicate(value, cb, root)));

        Optional.ofNullable(filter.getPosition())
                .ifPresent(value -> result.add(this.getPositionPredicate(value, cb, root)));

        Optional.ofNullable(filter.getUsername())
                .ifPresent(value -> result.add(this.getUserNamePredicate(value, cb, root)));

        Optional.ofNullable(filter.getAuthorities())
                .ifPresent(value -> result.add(this.getRolePredicate(value, cb, root)));

        return result.toArray(new Predicate[0]);
    }

    private Predicate getDateTimePredicate(LocalDateTime start,
                                           LocalDateTime stop,
                                           CriteriaBuilder cb,
                                           Root<UserModel> root) {
        return cb.between(root.get("createdDate"), start, stop);
    }

    private Predicate getUserNamePredicate(String username,
                                           CriteriaBuilder cb,
                                           Root<UserModel> root) {
        return cb.like(root.get("username"), "%" + username + "%");
    }

    private Predicate getDepartmentPredicate(String department,
                                             CriteriaBuilder cb,
                                             Root<UserModel> root) {
        return cb.like(root.get("department"), "%" + department + "%");
    }

    private Predicate getPositionPredicate(String position,
                                           CriteriaBuilder cb,
                                           Root<UserModel> root) {
        return cb.like(root.get("position"), "%" + position + "%");
    }

    private Predicate getFioPredicate(String fio,
                                      CriteriaBuilder cb,
                                      Root<UserModel> root) {
        return cb.like(root.get("fio"), "%" + fio + "%");
    }

    private Predicate getRolePredicate(String role,
                                       CriteriaBuilder cb,
                                       Root<UserModel> root) {
        Join<UserModel, RoleModel> authorities = root.join("authorities", JoinType.LEFT);
        return cb.equal(authorities.get("role").as(String.class), role);
    }

    private void getSort(UserModelFilterDto dto, CriteriaBuilder cb, Root<?> root, CriteriaQuery<?> cr) {

        if (Optional.ofNullable(dto.getSortField()).isPresent() &&
                Optional.ofNullable(dto.getDirection()).isPresent()) {

            cr.orderBy(dto.getDirection().equals(SortDirection.ASC)
                    ? cb.asc(root.get(dto.getSortField()))
                    : cb.desc(root.get(dto.getSortField())));
        }
    }
}
