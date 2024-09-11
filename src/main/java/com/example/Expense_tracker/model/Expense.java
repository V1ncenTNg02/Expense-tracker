package com.example.Expense_tracker.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "expense")
public class Expense {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  private double amount;

  private LocalDate date;

  private String category;

  public Expense(String description, double amount, java.time.LocalDate date, String category){
    this.description = description;
    this.amount = amount;
    this.date = date;
    this.category = category;
  };

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount){
    this.amount = amount;
  }

  public LocalDate getDate(){
    return date;
  }

  public void setDate(LocalDate date){
    this.date = date;
  }

  public String getCategory(){
    return category;
  }

  public void setCategory(String category){
    this.category = category;
  }
}
