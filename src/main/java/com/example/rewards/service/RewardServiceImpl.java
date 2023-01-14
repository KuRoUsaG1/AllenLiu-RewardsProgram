package com.example.rewards.service;

import com.example.rewards.domain.entity.Customer;
import com.example.rewards.domain.entity.CustomerRewards;
import com.example.rewards.domain.entity.Transaction;
import com.example.rewards.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service //TODO: 处理 exception，log
public class RewardServiceImpl implements RewardService{
    private final TransactionRepository transactionRepository;
    @Autowired
    public RewardServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<CustomerRewards> calculateRewardsPerMonth(LocalDate startDate, LocalDate endDate) {
        // Retrieve all transactions between the given dates
        List<Transaction> transactions = transactionRepository.findByDateBetween(startDate, endDate);

        Map<String, Integer> rewardsPerMonth = new HashMap<>();
        Map<Customer, Map<String, Integer>> customerMap = new HashMap<>();

        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            String month = date.getMonth() + "-" + date.getYear();
            Customer customer = transaction.getCustomer();
            int amount = (int) transaction.getAmount();
            Integer earnedPoints = rewardsPerMonth.getOrDefault(month, 0);
            if (amount > 100) {
                earnedPoints += 2 * (amount - 100);
                earnedPoints += 50;
            } else if (amount > 50) {
                earnedPoints += (amount - 50);
            }
            rewardsPerMonth.put(month, earnedPoints);
            customerMap.put(customer, rewardsPerMonth);
        }

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
}

