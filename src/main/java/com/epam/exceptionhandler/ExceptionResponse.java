package com.epam.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
	private String timeStamp;
	private String status;
	private String error;
	private String path;
}
