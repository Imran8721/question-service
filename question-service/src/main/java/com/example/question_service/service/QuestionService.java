package com.example.question_service.service;

import com.example.question_service.entity.Question;
import com.example.question_service.entity.QuestionWrapper;
import com.example.question_service.entity.Response;
import com.example.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;



    public ResponseEntity<List<Question>> getAllQuestions(){

        try{
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();

        }
        return  new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);




    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();

        }
        return  new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }



// To add a new question into the database
    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionRepository.save(question);
        return new ResponseEntity<>("Successfully added question",HttpStatus.CREATED);
    }
        catch (Exception e){
            e.printStackTrace();

        }
        return  new ResponseEntity<>("Error adding question",HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numOfQuestions) {
        List<Integer> questionIds = questionRepository.findRandomQuestionsByCategory(category,numOfQuestions);

        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> ids) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id : ids){
            questions.add(questionRepository.findById(id).get());
        }
        for(Question question : questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right=0;
        int i=0;
        for (Response r : responses) {
            Question question = questionRepository.findById(r.getId()).get();

            if(r.getResponse().equals(question.getRightAnswer())){
                right++;
            }

        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
