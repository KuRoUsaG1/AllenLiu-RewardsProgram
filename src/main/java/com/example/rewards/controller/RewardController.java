package com.example.rewards.controller;

import com.example.rewards.domain.entity.CustomerRewards;
import com.example.rewards.service.RewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<CustomerRewards>> calculateRewards(@RequestParam("startDate") LocalDate startDate,
                                                                  @RequestParam("endDate") LocalDate endDate) {
        List<CustomerRewards> rewards = rewardService.calculateRewardsPerMonth(startDate, endDate);
        if (rewards.isEmpty()) {
            log.error("No records found in the given date");
            return new ResponseEntity<>(rewards, HttpStatus.NOT_FOUND);
        }
        log.info("successfully return the information");
        return new ResponseEntity<>(rewards, HttpStatus.OK);
    }

    //TODO: handle global exception, internal error, resource not found, customized response check input range, type
}
