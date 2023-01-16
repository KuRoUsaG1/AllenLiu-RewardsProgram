package com.example.rewards.service;

import com.example.rewards.domain.entity.Customer;
import com.example.rewards.domain.entity.CustomerRewards;
import com.example.rewards.domain.entity.Transaction;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.service.impl.RewardServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RewardService rewardService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void calculateRewardsPerMonth() {

        rewardService = new RewardServiceImpl(transactionRepository);

        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 3, 31);

        // Create a list of mock transactions
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, new Customer(1L, "John Smith", ""), LocalDate.of(2022, 1, 15), 100));
        transactions.add(new Transaction(2L, new Customer(1L, "John Smith", ""), LocalDate.of(2022, 2, 15), 150));
        transactions.add(new Transaction(3L, new Customer(1L, "John Smith", ""), LocalDate.of(2022, 3, 15),  200));

        // Configure the mock repository to return the list of transactions when called
        when(transactionRepository.findByDateBetween(startDate, endDate)).thenReturn(transactions);

        // Call the method to be tested
        List<CustomerRewards> rewards = rewardService.calculateRewardsPerMonth(startDate, endDate);

        // Assert the results
        assertNotNull(rewards);
        assertTrue(rewards.size() > 0);
        System.out.println(rewards.get(0).getCustomerId() + " " + rewards.get(0).getMonth() + " " + rewards.get(0).getRewards());
        System.out.println(rewards.get(1).getCustomerId() + " " + rewards.get(1).getMonth() + " " + rewards.get(1).getRewards());
        System.out.println(rewards.get(2).getCustomerId() + " " + rewards.get(2).getMonth() + " " + rewards.get(2).getRewards());
        // check the rewards is calculated correctly
    }

    @After
    public void after() {
        System.gc();
    }
}