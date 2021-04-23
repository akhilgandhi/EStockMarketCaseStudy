package com.iiht.estock.company.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExceptionResponseMessage {

    private String message;
}
