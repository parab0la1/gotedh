package com.parab0la.gotedh.service;

import com.parab0la.gotedh.exception.UserNotFoundException;
import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.repository.DeckRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DeckService {

    private final DeckRepository deckRepository;
    private final UserService userService;

    public DeckService(DeckRepository deckRepository, UserService userService) {
        this.deckRepository = deckRepository;
        this.userService = userService;
    }

    public Deck createDeck(Long id, Deck deck) {
        userService.getUser(id)
                .map(user -> {
                    deck.setOwner(user);
                    return deckRepository.save(deck);
                })
                .orElseThrow(() -> new UserNotFoundException(id));

        return null;
    }

    public Optional<Deck> getDeck(Long id) {
        return deckRepository.findById(id);
    }

    public Iterable<Deck> getDecks() {
        return deckRepository.findAll();
    }

    public Deck updateDeck(Long id, Deck deck) {
        Optional<Deck> deckToUpdate = deckRepository.findById(id);

        if (deckToUpdate.isPresent()) {
            deckToUpdate.get().setCommander(deck.getCommander());
            deckRepository.save(deckToUpdate.get());

            return deckToUpdate.get();
        }

        return null;
    }

    public void deleteDeck(Long id) {
        if (deckRepository.existsById(id)) {
            deckRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
