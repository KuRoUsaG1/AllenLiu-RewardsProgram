package com.example.rewards.service.impl;

import com.example.rewards.domain.entity.Customer;
import com.example.rewards.domain.entity.CustomerRewards;
import com.example.rewards.domain.entity.Transaction;
import com.example.rewards.exception.ResourceNotFoundException;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.service.RewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class RewardServiceImpl implements RewardService {
    private final TransactionRepository transactionRepository;
    @Autowired
    public RewardServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<CustomerRewards> calculateRewardsPerMonth(LocalDate startDate, LocalDate endDate) {
        // Retrieve all transactions between the given dates

        List<Transaction> transactions = transactionRepository.findByDateBetween(startDate, endDate);
        if (transactions.isEmpty()) {
            log.error("No resource found in repo");
            throw new ResourceNotFoundException("No resource found in repo");
        }
        log.info("Transactions found in the date range");
        Map<Customer, Map<String, Integer>> customerMap = groupTransactionsByCustomer(transactions);
        return createCustomerRewards(customerMap);
    }
    private Map<Customer, Map<String, Integer>> groupTransactionsByCustomer(List<Transaction> transactions) {
        Map<String, Integer> rewardsPerMonth = new HashMap<>();
        Map<Customer, Map<String, Integer>> customerMap = new HashMap<>();
        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            String month = date.getMonth() + "-" + date.getYear();
            Customer customer = transaction.getCustomer();
            Integer earnedPoints = rewardsPerMonth.getOrDefault(month, 0);
            earnedPoints += calculateTransactionRewards(transaction);
            rewardsPerMonth.put(month, earnedPoints);
            customerMap.put(customer, rewardsPerMonth);
        }
        return customerMap;
    }

    private List<CustomerRewards> createCustomerRewards(Map<Customer, Map<String, Integer>> customerMap) {
        List<CustomerRewards> customerRewards = new ArrayList<>();
        for (Map.Entry<Customer, Map<String, Integer>> entry : customerMap.entrySet()) {
            Long customerId = entry.getKey().getId();
            Map<String, Integer> monthAndRewards = entry.getValue();
            for (Map.Entry<String, Integer> entry1 : monthAndRewards.entrySet()){
                String month = entry1.getKey();
                Integer rewards = entry1.getValue();
                customerRewards.add(new CustomerRewards(customerId, month, rewards));
            }
        }
        return customerRewards;
    }

    public CustomerRewards getTotalRewardsForCustomer(Long customerId){
        List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
        if (transactions.isEmpty()) {
            log.error("No transactions found for customer id : " + customerId);
            throw new ResourceNotFoundException("No transactions found for customer id : " + customerId);
        }
        log.info("Transactions found in the date range");
        CustomerRewards customerRewards = new CustomerRewards(customerId);
        int totalRewards = 0;
        for (Transaction transaction : transactions) {
            totalRewards += calculateTransactionRewards(transaction);
        }
        customerRewards.setRewards(totalRewards);
        return customerRewards;
    }

    private int calculateTransactionRewards(Transaction transaction) {
        int amount = (int) transaction.getAmount();
        int rewards = 0;
        if (amount > 100) {
            rewards += 2 * (amount - 100);
            rewards += 50;
        } else if (amount > 50) {
            rewards += (amount - 50);
        }
        return rewards;
    }
}

