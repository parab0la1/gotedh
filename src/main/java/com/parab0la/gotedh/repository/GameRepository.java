package com.parab0la.gotedh.repository;

import com.parab0la.gotedh.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}