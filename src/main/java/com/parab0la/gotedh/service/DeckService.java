package com.parab0la.gotedh.service;

import com.parab0la.gotedh.exception.UserNotFoundException;
import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.repository.DeckRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public Deck getUserDeck(Long userId, Long deckId) {
        Optional<User> owner = userService.getUser(userId);

        if (owner.isPresent()) {
            return deckRepository.findByDeckIdAndOwner(deckId, owner.get()).orElseThrow(() -> new UserNotFoundException(userId));
        }

        throw new UserNotFoundException(userId);
    }

    public List<Deck> getUserDecks(Long userId) {
        Optional<User> owner = userService.getUser(userId);

        if (owner.isPresent()) {
            return deckRepository.findByOwner(owner.get());
        }

        throw new UserNotFoundException(userId);
    }

    public List<Deck> getDecks() {
        return StreamSupport.stream(deckRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Deck updateDeck(Long userId, Long deckId, Deck newDeck) {
        if (userService.getUser(userId).isPresent()) {
            return deckRepository.findById(deckId)
                    .map(deckToUpdate -> deckRepository.save(updateRankings(deckToUpdate, newDeck)))
                    .orElse(null);
        }

        throw new UserNotFoundException(userId);
    }

    public void deleteDeck(Long userId, Long deckId) {
        if (!userService.getUser(userId).isPresent()) {
            throw new UserNotFoundException(userId);
        }

        if (deckRepository.existsById(deckId)) {
            deckRepository.deleteById(deckId);
        } else {
            throw new EntityNotFoundException();
        }
    }

    private Deck updateRankings(Deck deckToUpdate, Deck newDeck) {
        deckToUpdate.setEloRanking(newDeck.getEloRanking());
        deckToUpdate.setEloChangePerGame(newDeck.getEloChangePerGame());
        deckToUpdate.setGamesPlayed(newDeck.getGamesPlayed());
        deckToUpdate.setGamesWinPercent(newDeck.getGamesWinPercent());
        deckToUpdate.setOppsWinPercent(newDeck.getOppsWinPercent());

        return deckToUpdate;
    }
}
