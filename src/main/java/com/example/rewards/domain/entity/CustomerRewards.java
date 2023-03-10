package com.example.rewards.domain.entity;

public class CustomerRewards {
    private final Long customerId;
    private String month;
    private Integer rewards;

    public CustomerRewards(Long customerId){
        this.customerId = customerId;
        this.month = "N/A";
    }
    public CustomerRewards(Long customerId, String month, Integer rewards) {
        this.customerId = customerId;
        this.month = month;
        this.rewards = rewards;
    }

    public void setMonth(String month) {this.month = month;}

    public String getMonth() {
        return month;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Integer getRewards() {
        return rewards;
    }

    public void setRewards(Integer rewards) {
        this.rewards = rewards;
    }
}
