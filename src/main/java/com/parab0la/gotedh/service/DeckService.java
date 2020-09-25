package com.parab0la.gotedh.service;

import com.parab0la.gotedh.exception.DeckNotFoundException;
import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.repository.DeckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DeckService {

    private static final Logger logger = LoggerFactory.getLogger(DeckService.class);

    private final DeckRepository deckRepository;
    private final UserService userService;

    public DeckService(DeckRepository deckRepository, UserService userService) {
        this.deckRepository = deckRepository;
        this.userService = userService;
    }

    public Deck createDeck(Long userId, Deck deck) {
        User owner = userService.getUser(userId);

        logger.debug("Saving deck: {} with the owner: {} with id: {}",
                deck.getCommander(), owner.getName(), owner.getUserId());

        deck.setOwner(owner);

        return deckRepository.save(deck);
    }

    public Deck getDeck(Long deckId) {
        logger.debug("Getting deck with id: {}", deckId);

        return deckRepository.findById(deckId).orElseThrow(() -> new DeckNotFoundException(deckId));
    }

    public Deck getUserDeck(Long userId, Long deckId) {
        logger.debug("Getting deck with id: {} for the user with id: {}", deckId, userId);

        return deckRepository.findByDeckIdAndOwner(deckId, userService.getUser(userId))
                .orElseThrow(() -> new DeckNotFoundException(deckId));
    }

    public List<Deck> getDecks(Long userId) {
        logger.debug("Getting all decks for a user with id: {}", userId);

        return deckRepository.findByOwner(userService.getUser(userId));
    }

    public List<Deck> getDecks() {
        logger.debug("Getting all decks");

        return StreamSupport.stream(deckRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Deck updateDeck(Long userId, Long deckId, Deck newDeck) {
        logger.debug("Updating deck with id: {} for user: {} with the new deck: {}", deckId, userId, newDeck);

        Optional<Deck> optDeck = deckRepository.findByDeckIdAndOwner(deckId, userService.getUser(userId));

        return optDeck
                .map(deckToUpdate -> deckRepository.save(updateRankings(deckToUpdate, newDeck)))
                .orElseThrow(() -> new DeckNotFoundException(deckId));
    }

    public void deleteDeck(Long userId, Long deckId) {
        logger.debug("Deleting deck with id: {} for user with id: {}", deckId, userId);

        if (deckRepository.findByDeckIdAndOwner(deckId, userService.getUser(userId)).isPresent()) {
            logger.debug("Found a deck with id: {} for the user with id: {}", deckId, userId);
            deckRepository.deleteById(deckId);
        } else {
            logger.error("Did not find a deck with id: {} for the user with id: {}", deckId, userId);
        }
    }

    private Deck updateRankings(Deck deckToUpdate, Deck newDeck) {
        logger.debug("Updating the following values before saving deck: {}", newDeck);

        deckToUpdate.setEloRanking(newDeck.getEloRanking());
        deckToUpdate.setEloChangePerGame(newDeck.getEloChangePerGame());
        deckToUpdate.setGamesPlayed(newDeck.getGamesPlayed());
        deckToUpdate.setGamesWinPercent(newDeck.getGamesWinPercent());
        deckToUpdate.setOppsWinPercent(newDeck.getOppsWinPercent());

        return deckToUpdate;
    }
}
