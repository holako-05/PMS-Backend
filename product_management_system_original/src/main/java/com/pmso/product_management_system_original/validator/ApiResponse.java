package com.pmso.product_management_system_original.validator;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse {

    private final boolean success;
    private final String msg;

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }

}
