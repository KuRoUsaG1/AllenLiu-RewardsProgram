package com.example.rewards.service;

import com.example.rewards.domain.entity.CustomerRewards;

import java.time.LocalDate;
import java.util.List;

public interface RewardService {
    List<CustomerRewards> calculateRewardsPerMonth(LocalDate startDate, LocalDate endDate);

    CustomerRewards getTotalRewardsForCustomer(Long customerId);
}
