package com.bg.BoardGame.config;

import com.bg.BoardGame.dto.user.UserDto;
import com.bg.BoardGame.model.Category;
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
    }
}
