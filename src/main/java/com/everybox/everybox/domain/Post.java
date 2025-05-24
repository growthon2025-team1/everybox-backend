package com.everybox.everybox.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Enumerated(EnumType.STRING)
    private Long id;

    private String title;

    private int quantity;

    @Column(columnDefinition = "TEXT")
    private String details;

    private String location;

    // 좌표
    private Double lat;
    private Double lng;

    private String imageUrl;

    private Category category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isClosed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User giver;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

