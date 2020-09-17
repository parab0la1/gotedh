package com.parab0la.gotedh.service;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    public Deck createDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    public Optional<Deck> getDeck(Integer deckId) {
        return deckRepository.findById(deckId);
    }

    public Iterable<Deck> getDecks() {
        return deckRepository.findAll();
    }

    public Deck updateDeck(Integer deckid, Deck deck) {
        Optional<Deck> optDeckToUpdate = deckRepository.findById(deckid);

        if (optDeckToUpdate.isPresent()) {
            optDeckToUpdate.get().setCommander(deck.getCommander());
            deckRepository.save(optDeckToUpdate.get());

            return optDeckToUpdate.get();
        }

        return null;
    }

    public void deleteDeck(Integer deckId) {
        if (deckRepository.existsById(deckId)) {
            deckRepository.deleteById(deckId);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
