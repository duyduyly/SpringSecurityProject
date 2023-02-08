package com.bezkoder.springjwt.models.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor@NoArgsConstructor
@Setter@Getter@Builder
public class LoginRequest {
	@NotBlank
  	private String username;

	@NotBlank
	private String password;

}
