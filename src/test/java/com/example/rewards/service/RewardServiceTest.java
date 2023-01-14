package com.example.rewards.service;

import com.example.rewards.domain.entity.CustomerRewards;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.service.impl.RewardServiceImpl;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RewardServiceTest {

    @MockBean
    TransactionRepository transactionRepository;

    @Test
    public void calculateRewardsPerMonth() {
        RewardService rewardService = new RewardServiceImpl(transactionRepository);

        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 3, 31);

        // Call the method to be tested
        List<CustomerRewards> rewards = rewardService.calculateRewardsPerMonth(startDate, endDate);

        // Assert the results
        assertNotNull(rewards);
        assertTrue(rewards.size() > 0);
        // check the rewards is calculated correctly

    }
}