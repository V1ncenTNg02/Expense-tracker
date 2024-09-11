package com.example.Expense_tracker.controller;

import com.example.Expense_tracker.model.Expense;
import com.example.Expense_tracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
   private final ExpenseService expenseService;

   @GetMapping("/")
    public String home() {
        return "Home Page";
    }

   @GetMapping("/check")
   public ResponseEntity<String> checkConnection(){
       return ResponseEntity.ok("Connected successfully!");
   }

   public ExpenseController(ExpenseService expenseService){
    this.expenseService = expenseService;
   }

   @PostMapping("/add")
   public ResponseEntity<String> addExpense(@RequestParam String description, @RequestParam double amount){
    Expense expense = expenseService.addExpense(description, amount, "default");
    return ResponseEntity.ok("Expense added successfully (ID: "+ expense.getId() + ")");
   }

   @PutMapping("/update/{id}")
   public ResponseEntity<String> updateExpense(@PathVariable Long id, @RequestParam String description, @RequestParam double amount, @RequestParam String category){
    Optional<Expense> updatedExpense = expenseService.updateExpense(id,description,amount,category);
    return updatedExpense.isPresent()
                ? ResponseEntity.ok("Expense updated successfully (ID: " + updatedExpense.get().getId() + ")")
                : ResponseEntity.notFound().build();
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<String> deleteExpense(@PathVariable Long id){
    boolean isDeleted = expenseService.deleteExpense(id);
    return isDeleted ? ResponseEntity.ok("Expense deleted successfully") : ResponseEntity.notFound().build();
   }

   @GetMapping("/list")
   public ResponseEntity<List<Expense>> listExpense(){
    return ResponseEntity.ok(expenseService.getAllExpenses());
   }

   @GetMapping("/summary")
   public ResponseEntity<String> getSummary(@RequestParam(required = false) Integer month){
    double total;
    if(month == null){
      total = expenseService.getTotalExpenses();
      return ResponseEntity.ok("Total Expense: $" + total);
    }else{
      total = expenseService.getMonthlyExpenses(month);
      return ResponseEntity.ok("Total Expense for month:"+ month + ": $" + total);
    }
   }
}
