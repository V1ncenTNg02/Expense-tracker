package com.example.Expense_tracker.service;

import com.example.Expense_tracker.model.Expense;
import com.example.Expense_tracker.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

  private final ExpenseRepository expenseRepository;

  @Autowired
  public ExpenseService(ExpenseRepository expenseRepository) {
      this.expenseRepository = expenseRepository;
  }

  public Expense addExpense(String description, double amount, String category) {
    Expense expense = new Expense(description, amount, LocalDate.now(), category);
    return expenseRepository.save(expense);
  }

  public Optional<Expense> updateExpense(Long id, String description, double amount, String category) {
    Optional<Expense> existingExpense = expenseRepository.findById(id);
    if (existingExpense.isPresent()) {
      Expense expense = existingExpense.get();
      expense.setDescription(description);
      expense.setAmount(amount);
      expense.setCategory(category);
      return Optional.of(expenseRepository.save(expense));
    }
    return Optional.empty();
  }

  public boolean deleteExpense(Long id) {
    Optional<Expense> existingExpense = expenseRepository.findById(id);
    if (existingExpense.isPresent()) {
      expenseRepository.delete(existingExpense.get());
      return true;
    }
    return false;
  }

  public List<Expense> getAllExpenses(){
    return expenseRepository.findAll();
  }

  public double getTotalExpenses(){
    return expenseRepository.findAll().stream()
    .mapToDouble(Expense::getAmount)
    .sum();
  }

  public double getMonthlyExpenses(int month){
    YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
    LocalDate starDate = yearMonth.atDay(1);
    LocalDate endDate = yearMonth.atEndOfMonth();
    return expenseRepository.findByDateBetween(starDate, endDate).stream().mapToDouble(Expense::getAmount).sum();
  }
}
