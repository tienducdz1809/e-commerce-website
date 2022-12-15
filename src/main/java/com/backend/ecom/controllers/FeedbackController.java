package com.backend.ecom.controllers;

import com.backend.ecom.dto.feedback.FeedbackDTO;
import com.backend.ecom.dto.feedback.FeedbackRequestDTO;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.services.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {
    private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<FeedbackDTO> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @PutMapping("/update/{feedbackId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> updateFeedback(@Valid @PathVariable("feedbackId") Long feedbackId,
                                                         @Valid @RequestBody FeedbackRequestDTO feedbackRequest) {
        return feedbackService.updateFeedback(feedbackId, feedbackRequest);
    }

    @DeleteMapping("/delete/{feedbackId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> deleteFeedback(@Valid @PathVariable("feedbackId") Long feedbackId) {
        return feedbackService.deleteFeedback(feedbackId);
    }

}
