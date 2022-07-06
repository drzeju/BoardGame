package com.bg.BoardGame.service;

import com.bg.BoardGame.dto.GameDto;
import com.bg.BoardGame.exceptions.GameNotExistException;
import com.bg.BoardGame.model.Category;
import com.bg.BoardGame.model.Game;
import com.bg.BoardGame.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public void createGame(GameDto gameDto, Category category) {
        Game game = new Game();
        game.setDescription(gameDto.getDescription());
        game.setImageUrl(gameDto.getImageUrl());
        game.setName(gameDto.getName());
        game.setCategory(category);
        game.setPrice(gameDto.getPrice());
        gameRepository.save(game);
    }

    public GameDto getGameDto(Game game){
        GameDto gameDto = new GameDto();
        gameDto.setDescription(game.getDescription());
        gameDto.setImageUrl(game.getImageUrl());
        gameDto.setName(game.getName());
        gameDto.setCategoryId(game.getCategory().getId());
        gameDto.setPrice(game.getPrice());
        gameDto.setId(game.getId());
        return gameDto;
    }

    public List<GameDto> getAllGames() {
        List<Game> allGames = gameRepository.findAll();
        List<GameDto> gameDtos = new ArrayList<>();
        for (Game game: allGames){
            gameDtos.add(getGameDto(game));
        }
        return gameDtos;
    }

    public void updateGame(GameDto gameDto, Integer gameId) throws Exception {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (!optionalGame.isPresent()){
            throw new Exception("game is not present!");
        }
        Game game = optionalGame.get();
        game.setDescription(gameDto.getDescription());
        game.setImageUrl(gameDto.getImageUrl());
        game.setName(gameDto.getName());
        game.setPrice(gameDto.getPrice());
        gameRepository.save(game);
    }

    public void removeGame(Integer gameId) {
        gameRepository.deleteById(gameId);
    }

    public Game findById(Integer gameId) throws GameNotExistException {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isEmpty()) {
            throw new GameNotExistException("game id is not valid" + gameId);
        }
        return optionalGame.get();
    }
}
