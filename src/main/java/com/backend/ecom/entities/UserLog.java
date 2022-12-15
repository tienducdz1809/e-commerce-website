package com.backend.ecom.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @CreationTimestamp
    private LocalDateTime loginTime;

    public void addUser(User user){
        this.user = user;
        user.getUserLogs().add(this);
    }
}
