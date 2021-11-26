package com.epam.final_project.service;

import com.epam.final_project.entity.CategoryEntity;
import com.epam.final_project.entity.DifficultyEntity;
import com.epam.final_project.entity.QuizEntity;
import com.epam.final_project.model.Quiz;
import com.epam.final_project.repository.CategoryRepository;
import com.epam.final_project.repository.DifficultyRepository;
import com.epam.final_project.repository.QuizRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.*;

@Service
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;

    private final DifficultyRepository difficultyRepository;

    private final CategoryRepository categoryRepository;

    private final UsersQuizzesService usersQuizzesService;

    private final CategoryService categoryService;

    public QuizEntity save(QuizEntity quiz) {
        return quizRepository.save(quiz);
    }

    public List<QuizEntity> getAll() {
        return quizRepository.findAll();
    }

    public QuizService(QuizRepository quizRepository, DifficultyRepository difficultyRepository,
                       CategoryRepository categoryRepository, UsersQuizzesService usersQuizzesService,
                       CategoryService categoryService) {
        this.quizRepository = quizRepository;
        this.difficultyRepository = difficultyRepository;
        this.categoryRepository = categoryRepository;
        this.usersQuizzesService = usersQuizzesService;
        this.categoryService = categoryService;
    }

    public List<QuizEntity> getFirstFour() {
        List<QuizEntity> firstFourQuizzes = new ArrayList<>();
        List<QuizEntity> quizzes = quizRepository.findAllByOrderByIdDesc();
        for (int i = 0; i < quizzes.size(); i++) {
            if (i == 4) {
                return firstFourQuizzes;
            }
            firstFourQuizzes.add(quizzes.get(i));
        }
        return firstFourQuizzes;
    }

    public QuizEntity get(long id) throws NotFoundException {
        return quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Can not to get quiz"));
    }

    public List<QuizEntity> getBySubject(String subject) {
        return quizRepository.findAllByCategoryName(subject);
    }

    public List<QuizEntity> getAllOrderByName() {
        return quizRepository.findAllByOrderByName();
    }

    public List<QuizEntity> getAllOrderByDifficulty() {
        List<QuizEntity> quizzes = getAll();
        List<String> difficultyOrder = Arrays.asList("easy", "medium", "hard");
        Comparator<QuizEntity> quizComparator = Comparator.comparing(q -> difficultyOrder.indexOf(q.getDifficulty()));
        quizzes.sort(quizComparator);
        return quizzes;
    }

    public List<QuizEntity> getAllOrderByQuestions() {
        return quizRepository.findAllSortedByQuestions();
    }

    public void deleteById(long id) {
        quizRepository.deleteById(id);
    }

    public void showAllQuizzes(String sortBy, Model model) {
        List<QuizEntity> quizzes;
        List<CategoryEntity> categories = categoryService.getAll();
        if (sortBy.equalsIgnoreCase("name")) {
            quizzes = getAllOrderByName();
        } else if (sortBy.equalsIgnoreCase("difficulty")) {
            quizzes = getAllOrderByDifficulty();
        } else if (sortBy.equalsIgnoreCase("questions")) {
            quizzes = getAllOrderByDifficulty();
        } else {
            quizzes = getBySubject(sortBy);
        }
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("categories", categories);
        model.addAttribute("quizzes", quizzes);
    }

    public String showAll(Optional<Integer> page, Model model) {
        int currentPage = page.orElse(1);
        try {
            setCurrentPage(model, currentPage);
            return "/admin/quizzes";
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/admin/quizzes?error";
        }
    }

    private void setCurrentPage(Model model, int currentPage) throws NotFoundException {
        final int NUMBER_QUIZZES_ON_PAGE = 5;
        Page<QuizEntity> all = quizRepository.findAll(PageRequest.of(currentPage - 1, NUMBER_QUIZZES_ON_PAGE));
        model.addAttribute("quizPage", all);
    }

    public String getQuiz(Model model, long id) {
        try {
            QuizEntity entity = get(id);
            model.addAttribute("quiz",
                    Quiz.createQuiz(entity.getId(), entity.getName(), entity.getTime(),
                            entity.getDifficulty().getName(), entity.getCategory().getName()));
            return "/admin/edit-quiz";
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/admin/quizzes?error";
        }
    }

    public String add(Quiz quiz) {
        QuizEntity quizEntity = new QuizEntity();
        quizEntity.setName(quiz.getName());
        quizEntity.setTime(quiz.getTime());
        quizEntity.setDifficulty(getDifficulty(quiz));
        quizEntity.setCategory(getCategory(quiz));
        save(quizEntity);
        return "redirect:/admin/quizzes";
    }

    private DifficultyEntity getDifficulty(Quiz quiz) {
        if (!difficultyRepository.existsByName(quiz.getDifficulty())) {
            return DifficultyEntity.valueOf(quiz.getDifficulty());
        }
        return difficultyRepository.findByName(quiz.getDifficulty());
    }

    private CategoryEntity getCategory(Quiz quiz) {
        if (!categoryRepository.existsByName(quiz.getCategory())) {
            return CategoryEntity.valueOf(quiz.getCategory());
        }
        return categoryRepository.findByName(quiz.getCategory());
    }

    public String edit(Quiz quiz, long id) {
        QuizEntity newQuiz = null;
        try {
            newQuiz = get(id);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/admin/quizzes?error";
        }
        createNewQuiz(quiz, newQuiz);
        save(newQuiz);
        return "redirect:/admin/quizzes";
    }

    private void createNewQuiz(Quiz quiz, QuizEntity newQuiz) {
        if (!quiz.getName().isEmpty()) {
            newQuiz.setName(quiz.getName());
        }
        if (!quiz.getDifficulty().isEmpty()) {
            newQuiz.setDifficulty(getDifficulty(quiz));
        }
        if (!quiz.getCategory().isEmpty()) {
            newQuiz.setCategory(getCategory(quiz));
        }
        if (quiz.getTime() > 0) {
            newQuiz.setTime(quiz.getTime());
        }
    }

    public String showQuiz(long id, Model model, Principal principal) {
        int score = usersQuizzesService.getScoreByLogin(principal.getName(), id);
        if (score > -1) {
            model.addAttribute("score", score);
        }
        try {
            model.addAttribute("quiz", get(id));
            return "/app/quiz";
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "/app/all-quizzes";
        }
    }
}
