package com.epam.final_project.service;

import com.epam.final_project.entity.UserEntity;
import com.epam.final_project.model.RegistrationRequest;
import com.epam.final_project.model.User;
import com.epam.final_project.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final QuizService quizService;

    private final UsersQuizzesService usersQuizzesService;

    public String createUser(RegistrationRequest request) {
        if (!request.getPassword().equals(request.getMatchingPassword())
                || userExists(request.getEmail(), request.getLogin())) {
            return "/registration";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(request.getPassword());
        save(UserEntity.createUserEntity(request.getEmail(), request.getLogin(), password));
        return "login";
    }

    private boolean userExists(String email, String login) {
        if (getByEmail(email) != null) {
            return true;
        }
        return getByLogin(login) != null;
    }

    public List<UserEntity> getAll() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }

    public String getUser(Model model, long id) {
        try {
            UserEntity entity = get(id);
            model.addAttribute("user",
                    User.createUser(entity.getId(), entity.getLogin(), entity.getEmail(), entity.getAccessLevel()));
            return "/admin/edit-user";
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/admin/users?error";
        }
    }

    public UserEntity get(long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Can not to get user"));
    }

    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserEntity getByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public String showAll(Optional<Integer> page, Model model) {
        int currentPage = page.orElse(1);
        try {
            setCurrentPage(model, currentPage);
            return "/admin/users";
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/admin/home?error";
        }
    }

    private void setCurrentPage(Model model, int currentPage) throws NotFoundException {
        final int NUMBER_USERS_ON_PAGE = 5;
        Page<UserEntity> all = userRepository.findAll(PageRequest.of(currentPage - 1, NUMBER_USERS_ON_PAGE));
        if (!all.hasContent()) {
            throw new NotFoundException("Can not to get user by range");
        }
        model.addAttribute("userPage", all);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public void updateAccessLevel(long id, String state) {
        userRepository.updateAccessLevel(id, state);
    }

    public void showProfile(Model model, Principal principal) {
        model.addAttribute("user", getByLogin(principal.getName()));
        model.addAttribute("quizzes", usersQuizzesService.getAllByUserLogin(principal.getName()));
    }

    public void showHome(Model model, Principal principal) {
        model.addAttribute("quizzes", quizService.getFirstFour());
        model.addAttribute("userQuizzes", usersQuizzesService.getFirstFourUserQuizzes(principal.getName()));
    }

    public UserService(UserRepository userRepository, QuizService quizService, UsersQuizzesService usersQuizzesService) {
        this.userRepository = userRepository;
        this.quizService = quizService;
        this.usersQuizzesService = usersQuizzesService;
    }

    public String edit(User user, long id) {
        UserEntity newUser = null;
        try {
            newUser = get(id);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/admin/users";
        }
        createNewUser(user, newUser);
        save(newUser);
        return "redirect:/admin/users";
    }

    private void createNewUser(User user, UserEntity newUser) {
        if (!user.getLogin().isEmpty()) {
            newUser.setLogin(user.getLogin());
        }
        if (!user.getEmail().isEmpty()) {
            newUser.setEmail(user.getEmail());
        }
        if (!user.getAccessLevel().isEmpty()) {
            newUser.setAccessLevel(user.getAccessLevel());
        }
        if (!user.getLogin().isEmpty()) {
            newUser.setLogin(user.getLogin());
        }
    }

}
