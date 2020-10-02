package com.parab0la.gotedh;

import com.parab0la.gotedh.dto.DeckDTO;
import com.parab0la.gotedh.dto.GameDTO;
import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.Game;
import com.parab0la.gotedh.model.User;

import java.util.List;

public abstract class TestRoot {

    protected List<Deck> decks;
    protected List<User> users;
    protected List<DeckDTO> deckDTOs;
    protected Deck deck;
    protected Deck deckTwo;
    protected Deck deckThree;
    protected Deck deckFour;
    protected DeckDTO deckDTO;
    protected User user;
    protected User userTwo;
    protected User userThree;
    protected DeckDTO deckDTOOne;
    protected DeckDTO deckDTOTwo;
    protected DeckDTO deckDTOThree;
    protected DeckDTO deckDTOFour;
    protected GameDTO gameDTO;
    protected Game game;
    protected final Long PHONY_USER_ID = 457L;
    protected final Long PHONY_DECK_ID = 458L;
    protected final Long USER_ID = 1L;
    protected final Long DECK_ID_KALAMAX = 1L;
    protected final Long DECK_ID_ANJE = 2L;
    protected final Long DECK_ID_KENRITH = 3L;
    protected final Long DECK_ID_OMNATH = 4L;
    protected final Long USER_ID_BRUCE_WAYNE = 1L;
    protected final Long USER_ID_HARVEY_DENT = 2L;
    protected final Long USER_ID_ALFRED_PENNYWORTH = 3L;
    protected final String KALAMAX = "Kalamax";
    protected final String ANJE = "Anje";
    protected final String KENRITH = "Kenrith";
    protected final String OMNATH = "Omnath";
    protected final String USER_NOT_FOUND_MSG = "The user with id: " + PHONY_USER_ID + " could not be found";
    protected final String DECK_NOT_FOUND_MSG = "The deck with id: " + PHONY_DECK_ID + " could not be found";
    protected final String KALAMAX_DECK_NOT_FOUND_MSG = "The deck with id: " + DECK_ID_KALAMAX + " could not be found";
    protected final String BRUCE_WAYNE = "Bruce Wayne";
    protected final String HARVEY_DENT = "Harvey Dent";
    protected final String ALFRED_PENNYWORTH = "Alfred Pennyworth";
}
