package com.example.tsekh_task.payload.response;

import com.example.tsekh_task.entity.Washer;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WasherDto {
    private String name;
    private int telephoneNumber;
    private int stake;
    private boolean isActive;

    public WasherDto(Washer washer) {
        this.name = washer.getName();
        this.telephoneNumber = washer.getTelephoneNumber();
        this.stake = washer.getStake();
        this.isActive = washer.isActive();
    }
}
