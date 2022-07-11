package com.bg.BoardGame.controller;

import com.bg.BoardGame.common.ApiResponse;
import com.bg.BoardGame.dto.GameDto;
import com.bg.BoardGame.exceptions.CustomException;
import com.bg.BoardGame.model.Category;
import com.bg.BoardGame.model.Game;
import com.bg.BoardGame.repository.CategoryRepository;
import com.bg.BoardGame.repository.GameRepository;
import com.bg.BoardGame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    GameRepository gameRepository;



//    @RolesAllowed("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/add")
    public ResponseEntity<ApiResponse> createGame(@RequestBody GameDto gameDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(gameDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"category does not exists"), HttpStatus.BAD_REQUEST);
        }
        gameService.createGame(gameDto,optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true,"game has been added"), HttpStatus.CREATED);
    }

//    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @GetMapping("/user")
    public ResponseEntity<List<GameDto>> getGames(){
        List<GameDto> games = gameService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @GetMapping("/user/sorted")
    public ResponseEntity<List<Game>> getSortedGames(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String sortDir) {

        List<Game> list = gameService.getSortedGames(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<List<Game>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @GetMapping("/user/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable("gameId") Integer gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if(!optionalGame.isPresent()){
            throw  new CustomException("Game does not exists.");
        }
        Game game = gameService.findById(gameId);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

//    @RolesAllowed("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/update/{gameId}")
    public ResponseEntity<ApiResponse> updateGame(@PathVariable("gameId") Integer gameId, @RequestBody GameDto gameDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(gameDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<ApiResponse>( new ApiResponse(false,"category does not exists."), HttpStatus.BAD_REQUEST);
        }
        gameService.updateGame(gameDto, gameId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "game has been updated."), HttpStatus.OK);
    }

//    @RolesAllowed("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/remove/{gameId}")
    public ResponseEntity<ApiResponse> removeGame(@PathVariable("gameId") Integer gameId){
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if(!optionalGame.isPresent()){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"game does not exist."), HttpStatus.BAD_REQUEST);
        }
        gameService.removeGame(gameId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"game has been removed."), HttpStatus.OK);
    }
}
