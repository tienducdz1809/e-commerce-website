package com.backend.ecom.services;

import com.backend.ecom.dto.feedback.FeedbackDTO;
import com.backend.ecom.dto.feedback.FeedbackRequestDTO;
import com.backend.ecom.entities.Feedback;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.repositories.FeedbackRepository;
import com.backend.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    public FeedbackRepository feedbackRepository;

    @Autowired
    public UserRepository userRepository;


    public List<FeedbackDTO> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
        feedbacks.forEach(feedback -> feedbackDTOS.add(new FeedbackDTO(feedback)));
        return feedbackDTOS;
    }

    public ResponseEntity<ResponseObject> updateFeedback(Long feedbackId, FeedbackRequestDTO feedbackRequest) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found feedback with id:" + feedbackId));
        feedback.setContent(feedbackRequest.getContent());
        feedback.setRating(feedbackRequest.getRating());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update feedback successfully", feedbackRepository.save(feedback)));
    }

    public ResponseEntity<ResponseObject> deleteFeedback(Long feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new ResourceNotFoundException("Not found discount with id:" + feedbackId);
        }
        feedbackRepository.deleteById(feedbackId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete feedback successfully", ""));
    }

}
