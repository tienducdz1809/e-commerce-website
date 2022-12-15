package com.backend.ecom.entities;

import com.backend.ecom.dto.user.UserCreateRequestDTO;
import com.backend.ecom.payload.request.SignupRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@AllArgsConstructor
@Entity
@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String ava;

    private String fullName;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private String address;

    @ManyToOne
    private Role role;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Feedback> feedbacks = new java.util.LinkedHashSet<>();

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private Cart cart;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Transaction> transactions = new java.util.LinkedHashSet<>();

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @JsonIgnore
    private int passwordResetCode;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    private LocalDateTime deletedAt;

    @Transient
    private Duration lastLogin;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserLog> userLogs = new java.util.LinkedHashSet<>();

    public User(SignupRequest signUpRequest) {
        this.fullName = signUpRequest.getFullName();
        this.username = signUpRequest.getUsername();
        this.email = signUpRequest.getEmail();
        this.address = signUpRequest.getAddress();
    }

    public User(UserCreateRequestDTO userCreateRequestDTO) {
        this.fullName = userCreateRequestDTO.getFullName();
        this.username = userCreateRequestDTO.getUsername();
        this.email = userCreateRequestDTO.getEmail();
    }

    public Duration getLastLogin() {
        if (userLogs.size() == 0) {
            return lastLogin = null;
        } else {
            return lastLogin = Duration.between(userLogs.stream().toList().get(userLogs.size() - 1).getLoginTime(), LocalDateTime.now());
        }
    }
}
