package com.bg.BoardGame.config;

import com.bg.BoardGame.dto.user.UserDto;
import com.bg.BoardGame.model.Category;
import com.bg.BoardGame.model.Game;
import com.bg.BoardGame.model.Role;
import com.bg.BoardGame.model.User;
import com.bg.BoardGame.repository.CategoryRepository;
import com.bg.BoardGame.repository.GameRepository;
import com.bg.BoardGame.repository.RoleRepository;
import com.bg.BoardGame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private GameRepository gameRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, CategoryRepository categoryRepository, GameRepository gameRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        roleRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        gameRepository.deleteAll();

        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_USER"));

        userRepository.save(new User("Admin","Admin", "admin@test.com","21232F297A57A5A743894A0E4A801FC3","ROLE_ADMIN",roleRepository.getById(1)));

        categoryRepository.save(new Category("Deckbuilder","Build deck to win","none"));
        categoryRepository.save(new Category("Worker Placement","Place you worker, make resources and build","none"));
        categoryRepository.save(new Category("Area Control","Recruit units, place them on map, and control more territories","none"));
        categoryRepository.save(new Category("Engine Builder","Create the most efficient engine to make more points ","none"));

        gameRepository.save(new Game("Blood Rage","none",20.99,"none",categoryRepository.getById(3)));
        gameRepository.save(new Game("Stone Age","none",15.99,"none",categoryRepository.getById(2)));
        gameRepository.save(new Game("Clank!","none",21.99,"none",categoryRepository.getById(1)));
        gameRepository.save(new Game("Star Realms","none",10.99,"none",categoryRepository.getById(1)));
        gameRepository.save(new Game("Inis","none",22.99,"none",categoryRepository.getById(3)));
        gameRepository.save(new Game("Terraforming Mars","none",17.99,"none",categoryRepository.getById(4)));
    }
}
