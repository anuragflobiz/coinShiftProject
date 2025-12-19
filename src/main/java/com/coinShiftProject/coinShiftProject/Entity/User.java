package com.coinShiftProject.coinShiftProject.Entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "mobile")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(nullable = false)
    private String password; //store in hashed form

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> walletList=new ArrayList<>();

    @OneToMany(mappedBy = "receiverUser")
    private List<Transaction> receiverTransactionList=new ArrayList<>();

    @OneToMany(mappedBy = "senderUser")
    private List<Transaction> senderTransactionList=new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public User(UUID id, String name, String email, String mobile, String password, LocalDateTime createdAt, List<Wallet> walletList, List<Transaction> receiverTransactionList, List<Transaction> senderTransactionList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.createdAt = createdAt;
        this.walletList = walletList;
        this.receiverTransactionList = receiverTransactionList;
        this.senderTransactionList = senderTransactionList;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Wallet> getWalletList() {
        return walletList;
    }

    public void setWalletList(List<Wallet> walletList) {
        this.walletList = walletList;
    }

    public List<Transaction> getReceiverTransactionList() {
        return receiverTransactionList;
    }

    public void setReceiverTransactionList(List<Transaction> receiverTransactionList) {
        this.receiverTransactionList = receiverTransactionList;
    }

    public List<Transaction> getSenderTransactionList() {
        return senderTransactionList;
    }

    public void setSenderTransactionList(List<Transaction> senderTransactionList) {
        this.senderTransactionList = senderTransactionList;
    }
}
