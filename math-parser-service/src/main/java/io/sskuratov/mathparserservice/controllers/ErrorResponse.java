package io.sskuratov.mathparserservice.controllers;

import lombok.Value;

@Value
public class ErrorResponse {

    private int errorCode;
    private String errorMessage;
}
