package com.epam.final_project.service;

import com.epam.final_project.entity.QuizEntity;
import com.epam.final_project.entity.UsersQuizzesEntity;
import com.epam.final_project.repository.UsersQuizzesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsersQuizzesService {

    private final UsersQuizzesRepository usersQuizzesRepository;

    public void insert(UsersQuizzesEntity entity) {
        entity = usersQuizzesRepository.save(entity);
    }

    public List<QuizEntity> getQuizzesByUserId(long id) {
        List<UsersQuizzesEntity> usersQuizzesEntities = usersQuizzesRepository.findAllByUserId(id);
        List<QuizEntity> quizzes = new ArrayList<>();
        for (UsersQuizzesEntity temp : usersQuizzesEntities) {
            quizzes.add(temp.getQuiz());
        }
        return quizzes;
    }

    public List<QuizEntity> getQuizzesByUserLogin(String login) {
        List<UsersQuizzesEntity> usersQuizzesEntities = usersQuizzesRepository.findAllByUserLogin(login);
        List<QuizEntity> quizzes = new ArrayList<>();
        for (UsersQuizzesEntity temp : usersQuizzesEntities) {
            quizzes.add(temp.getQuiz());
        }
        return quizzes;
    }

    public List<UsersQuizzesEntity> getAllByUserLogin(String login) {
        return usersQuizzesRepository.findAllByUserLogin(login);
    }

    public List<QuizEntity> getFirstFourUserQuizzes(String login) {
        List<UsersQuizzesEntity> usersQuizzesEntities = usersQuizzesRepository.findAllByUserLogin(login);
        List<QuizEntity> quizzes = new ArrayList<>();
        for (int i = 0; i < usersQuizzesEntities.size(); i++) {
            if (i == 4) {
                return quizzes;
            }
            quizzes.add(usersQuizzesEntities.get(i).getQuiz());
        }
        return quizzes;
    }

    public int getScoreByLogin(String login, long quizId) {
        Optional<UsersQuizzesEntity> entity = usersQuizzesRepository.findByUserLoginAndQuizId(login, quizId);
        if (entity.isPresent()) {
            return entity.get().getScore();
        }
        return -1;
    }

    public UsersQuizzesEntity getByUserIdAndQuizId(long userId, long quizId) {
        return usersQuizzesRepository.findByUserIdAndQuizId(userId, quizId);
    }

    public UsersQuizzesService(UsersQuizzesRepository usersQuizzesRepository) {
        this.usersQuizzesRepository = usersQuizzesRepository;
    }

    public void showUserQuizzes(Model model, Principal principal) {
        model.addAttribute("userQuizzes", getQuizzesByUserLogin(principal.getName()));
    }
}
