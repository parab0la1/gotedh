package com.parab0la.gotedh.repository;

import com.parab0la.gotedh.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
