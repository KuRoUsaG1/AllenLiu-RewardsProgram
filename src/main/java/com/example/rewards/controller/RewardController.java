package com.example.rewards.controller;

import com.example.rewards.domain.entity.CustomerRewards;
import com.example.rewards.service.RewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class RewardController {
    private final RewardService rewardService;
    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }
    @GetMapping("/calculate-rewards")
    public ResponseEntity<List<CustomerRewards>> calculateRewards(@RequestParam("startDate") @DateTimeFormat(iso =
                                                                          DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                  @RequestParam("endDate") @DateTimeFormat(iso =
                                                                          DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            log.error("Invalid date range, start date should be before end date");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CustomerRewards> rewards = rewardService.calculateRewardsPerMonth(startDate, endDate);
        if (rewards.isEmpty()) {
            log.error("No records found during the given date");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("successfully return the information");
        return new ResponseEntity<>(rewards, HttpStatus.OK);
    }

    @GetMapping("/total-rewards-for-customer")
    public ResponseEntity<CustomerRewards> getTotalRewardsForCustomer(@RequestParam("customerId") Long customerId) {
        CustomerRewards customerRewards = rewardService.getTotalRewardsForCustomer(customerId);
        if (customerRewards.getRewards() <= 0) {
            log.error("No records found for the customer during the given date range");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Successfully return the total rewards for the customer");
        return new ResponseEntity<>(customerRewards, HttpStatus.OK);
    }
}
