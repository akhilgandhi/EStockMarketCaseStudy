package com.iiht.estock.stockprices.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ExceptionResponseMessage {

    private String message;
}
