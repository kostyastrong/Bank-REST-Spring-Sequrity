package ru.mipt.springtask.DTO;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Builder
public class AccountDTO {
    @NotNull
    public Long balance = 0L;

    @NotNull
    public Long userId;
}
