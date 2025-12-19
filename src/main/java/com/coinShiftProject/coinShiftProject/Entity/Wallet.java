package com.coinShiftProject.coinShiftProject.Entity;


import com.coinShiftProject.coinShiftProject.enums.CurrencyCode;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "wallets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id","currencyCode"})
        }
)

public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false,unique = true)
    private CurrencyCode currencyCode;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "senderWallet")
    private List<Transaction> senderTransaction=new ArrayList<>();

    @OneToMany(mappedBy = "receiverWallet")
    private List<Transaction> receiverTransaction=new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Wallet(UUID id, CurrencyCode currencyCode, BigDecimal balance, LocalDateTime createdAt, LocalDateTime updatedAt, User user, List<Transaction> senderTransaction, List<Transaction> receiverTransaction) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.senderTransaction = senderTransaction;
        this.receiverTransaction = receiverTransaction;
    }

    public Wallet() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getSenderTransaction() {
        return senderTransaction;
    }

    public void setSenderTransaction(List<Transaction> senderTransaction) {
        this.senderTransaction = senderTransaction;
    }

    public List<Transaction> getReceiverTransaction() {
        return receiverTransaction;
    }

    public void setReceiverTransaction(List<Transaction> receiverTransaction) {
        this.receiverTransaction = receiverTransaction;
    }
}