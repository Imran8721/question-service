package com.example.question_service.controller;

import com.example.question_service.entity.Question;
import com.example.question_service.entity.QuestionWrapper;
import com.example.question_service.entity.Response;
import com.example.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {

        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable  String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    //generate quiz
    //get questions based on quiz id
    //calculate score here


    //this method returns the list of IDs of questions for quiz
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuiz(@RequestParam String category, @RequestParam Integer numOfQuestions) {

        return  questionService.getQuestionsForQuiz(category,numOfQuestions);

    }
    @PostMapping("getQuestions")
    public  ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> ids) {
        return  questionService.getQuestionsFromId(ids);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

    //below code is for testing config server

    @Value("${myapp.custom.property}")
    private String customProperty;

    @GetMapping("/property")
    public String getProperty() {
        return customProperty;
    }


}
