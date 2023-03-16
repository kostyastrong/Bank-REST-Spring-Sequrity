package ru.mipt.springtask.DTO;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class TransactionDTO {
    @NotNull
    public Long account_from;
    @NotNull
    public Long account_to;
    @NotNull
    public Long money;
    @NotNull
    public boolean cancelled;
}
