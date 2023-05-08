package com.pmso.product_management_system_original.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDescription {

    private Date DateError;
    private String message;
    private String description;

}
