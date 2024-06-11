package com.rungroup.Dietly.DTO;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietDTO {
    private int fishPercentage;
    private int vegetablePercentage;
    private int meatPercentage;
    @NotNull
    @FutureOrPresent(message = "Date must be in the future or present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;





}
