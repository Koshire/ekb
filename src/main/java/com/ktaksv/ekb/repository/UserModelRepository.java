package com.ktaksv.ekb.repository;

import com.ktaksv.ekb.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModelRepository extends BaseRepository<UserModel, Long> {

    Optional<UserModel> findByUsername(String login);
}
