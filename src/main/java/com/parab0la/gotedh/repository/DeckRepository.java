package com.parab0la.gotedh.repository;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends CrudRepository<Deck, Long> {
    Optional<Deck> findByDeckIdAndOwner(Long deckId, User owner);

    List<Deck> findByOwner(User owner);
}
