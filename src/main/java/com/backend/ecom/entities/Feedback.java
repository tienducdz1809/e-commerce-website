package com.backend.ecom.entities;

import com.backend.ecom.dto.feedback.FeedbackRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    private Integer rating;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private Product product;

    @CreationTimestamp
    private LocalDateTime createdDate;

    public Feedback(FeedbackRequestDTO feedbackRequestDTO){
        this.content = feedbackRequestDTO.getContent();
        this.rating = feedbackRequestDTO.getRating();
    }


}
