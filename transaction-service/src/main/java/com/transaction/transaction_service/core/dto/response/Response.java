package com.transaction.transaction_service.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private String responseCode = "00";

    private String responseMessage;

    private T data;

    public Response(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Response(String responseMessage, String responseCode) {
        this.responseMessage = responseMessage;
        this.responseCode = responseCode;
    }
}