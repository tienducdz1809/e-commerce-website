package com.backend.ecom.dto.feedback;

import com.backend.ecom.entities.Feedback;
import com.backend.ecom.entities.Product;
import com.backend.ecom.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO {
    private long id;
    private String content;

    private Integer rating;

    private String user;

    private String product;

    private LocalDateTime createdDate;

    public FeedbackDTO(Feedback feedback){
        this.id = feedback.getId();
        this.content = feedback.getContent();
        this.rating = feedback.getRating();
        this.user = feedback.getUser().getUsername();
        this.product = feedback.getProduct().getName();
        this.createdDate = feedback.getCreatedDate();
    }
}
