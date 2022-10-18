package com.tutego.date4u.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UnicornDTO {

	private String email;
	@JsonProperty( access = JsonProperty.Access.WRITE_ONLY )
	private String password;

	public UnicornDTO( String email, String password ) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UnicornDTO{" +
		       "email='" + email + '\'' +
		       ", password='" + password + '\'' +
		       '}';
	}
}
