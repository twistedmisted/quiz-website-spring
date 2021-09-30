package com.epam.final_project.service;

import com.epam.final_project.entity.*;
import com.epam.final_project.model.Question;
import com.epam.final_project.model.QuestionRequest;
import com.epam.final_project.repository.QuestionRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final AnswerService answerService;

    private final VariantService variantService;

    private final QuizService quizService;

    private final UsersQuizzesService usersQuizzesService;

    private final UserService userService;

    public QuestionEntity save(QuestionEntity question) {
        return questionRepository.save(question);
    }

    public Page<QuestionEntity> getQuestionPage(long id, int startItem) throws NotFoundException {
        Page<QuestionEntity> pageResult = questionRepository.findAllByQuizIdId(id, PageRequest.of(startItem, 1));
        if (pageResult.hasContent()) {
            return pageResult;
        }
        throw new NotFoundException("Can not to get question by range");
    }

    public long getCountByQuizId(long id) {
        return questionRepository.countAllByQuizIdId(id);
    }

    public List<QuestionEntity> getAllByQuizId(long id) throws NotFoundException {
        List<QuestionEntity> questions = questionRepository.findAllByQuizIdId(id);
        if (questions.isEmpty()) {
            throw new NotFoundException("Can not to get list of quizzes");
        }
        return questions;
    }

    public String addQuestion(long id, Question question) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setPrompt(question.getPrompt());
        try {
            questionEntity.setQuizId(quizService.get(id));
            questionEntity = save(questionEntity);
            saveAnswers(question, questionEntity);
            saveVariants(question, questionEntity);
            return "redirect:/admin/quizzes/{id}/questions";
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/admin/quizzes/{id}/questions/add";
        }
    }

    private void saveVariants(Question question, QuestionEntity questionEntity) {
        for (String variant : question.getVariants()) {
            VariantEntity variantEntity = new VariantEntity();
            variantEntity.setOption(variant);
            variantEntity.setQuestion(questionEntity);
            variantService.save(variantEntity);
        }
    }

    private void saveAnswers(Question question, QuestionEntity questionEntity) {
        while (question.getAnswers().remove(null)) {
        }
        ;
        for (String answer : question.getAnswers()) {
            AnswerEntity answerEntity = new AnswerEntity();
            answerEntity.setAnswer(answer);
            answerEntity.setQuestion(questionEntity);
            answerService.save(answerEntity);
        }
    }

    public String showAllByQuizId(long id, Model model) {
        try {
            List<QuestionEntity> questions = getAllByQuizId(id);
            model.addAttribute("quizId", id);
            model.addAttribute("questions", questions);
            return "/admin/questions";
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "redirect:/admin/quizzes?error";
        }
    }

    public void deleteById(long id) {
        questionRepository.deleteById(id);
    }

    public QuestionService(QuestionRepository questionRepository,
                           AnswerService answerService,
                           VariantService variantService,
                           QuizService quizService, UsersQuizzesService usersQuizzesService, UserService userService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
        this.variantService = variantService;
        this.quizService = quizService;
        this.usersQuizzesService = usersQuizzesService;
        this.userService = userService;
    }

    public String showByQuizId(long id, int index, HttpServletRequest request, Model model, Principal principal) {
        try {
            usersQuizzesService.insert(UsersQuizzesEntity
                    .create(userService.getByLogin(principal.getName()), quizService.get(id)));
            setupSession(request, id);
            setTimer(request, model);
            setupModel(id, index, model);
            model.addAttribute("question", new QuestionRequest());
            if (model.getAttribute("page") == null) {
                clearSession(request);
                return "redirect:/app/quiz/{id}";
            }
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "/app/home";
        }
        return "/app/question";
    }

    public String showNextQuestion(long id, int index,
                                   String answers, HttpServletRequest request,
                                   Model model, Principal principal) {
        try {
            if (checkTime(request)) {
                clearSession(request);
                updateScore(request, id, principal.getName());
                return "redirect:/app/quiz/{id}";
            }
            checkAnswers(request, id, index, answers);
            setTimer(request, model);
            setupModel(id, index, model);
            if (model.getAttribute("page") == null) {
                updateScore(request, id, principal.getName());
                clearSession(request);
                return "redirect:/app/quiz/{id}";
            }
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return "/app/home";
        }
        return "/app/question";
    }

    private void setupSession(HttpServletRequest request, long id) throws NotFoundException {
        QuizEntity quiz = quizService.get(id);
        HttpSession session = request.getSession();
        if (session.getAttribute("quizFinishAt") != null) {
            return;
        }
        int timeForQuiz = quiz.getTime();
        long quizFinishAt = System.currentTimeMillis() + (long) timeForQuiz * 60 * 1000;
        session.setAttribute("quizFinishAt", new Date(quizFinishAt));
        session.setAttribute("score", 0);
    }

    private void setTimer(HttpServletRequest request, Model model) throws NotFoundException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = ((Date) request.getSession().getAttribute("quizFinishAt"))
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        long seconds = Duration.between(now, end).getSeconds();
        model.addAttribute("seconds", seconds);
    }

    private void setupModel(long id, int index, Model model) throws NotFoundException {
        if (--index >= getCountByQuizId(id)) {
            model.addAttribute("page", null);
            return;
        }
        model.addAttribute("page", getQuestionPage(id, index));
        model.addAttribute("letters", Arrays.asList('a', 'b', 'c', 'd'));
        model.addAttribute("quizId", id);
        model.addAttribute("questionIndex", index);
    }

    private void clearSession(HttpServletRequest request) {
        request.getSession().removeAttribute("score");
        request.getSession().removeAttribute("quizFinishAt");
    }

    private void checkAnswers(HttpServletRequest request, long id, int index, String answers) throws NotFoundException {
        int score = (int) request.getSession().getAttribute("score");
        List<String> userAnswers = Arrays.asList(answers.split(","));
        List<String> questionAnswers = null;
        try {
            questionAnswers = answerService
                    .getAllByQuestionId(getAllByQuizId(id).get(index - 2).getId());
            Collections.sort(userAnswers);
            Collections.sort(questionAnswers);
            if (userAnswers.equals(questionAnswers)) {
                score++;
            }
            request.getSession().setAttribute("score", score);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("Can not to get question answers");
        }
    }

    private boolean checkTime(HttpServletRequest request) {
        Date timeNow = new Date();
        Date quizEndTime = (Date) request.getSession().getAttribute("quizFinishAt");
        return timeNow.compareTo(quizEndTime) > -1;
    }

    private void updateScore(HttpServletRequest request, long id, String login) throws NotFoundException {
        if (request.getSession().getAttribute("score") == null) {
            return;
        }
        int score = (int) request.getSession().getAttribute("score");
        score = (int) (score * 100 / getCountByQuizId(id));
        UsersQuizzesEntity entity = usersQuizzesService
                .getByUserIdAndQuizId(userService.getByLogin(login).getId(), id);
        entity.setScore(score);
        usersQuizzesService.insert(entity);
    }
}
