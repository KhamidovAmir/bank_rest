package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_card_id", nullable = false)
    private Card fromCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_card_id", nullable = false)
    private Card toCard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferStatus status;

    private String failureReason;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt ;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }
    @PreUpdate
    protected void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
