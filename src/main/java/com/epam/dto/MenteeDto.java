package com.epam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MenteeDto {
	@Schema(accessMode = AccessMode.READ_ONLY)
	private int menteeId;
	@NotBlank(message="Please provide valid name..")
	private String menteeName;
	@Email(message = "please provide valid email..")
	private String email;
}
