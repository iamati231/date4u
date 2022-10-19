package com.tutego.date4u.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnicornDTO {

	private String email;
	@JsonProperty( access = JsonProperty.Access.WRITE_ONLY )
	private String password;

	@JsonProperty( access = JsonProperty.Access.WRITE_ONLY )
	private String confirmPassword;

	public UnicornDTO( String email, String password, String confirmPassword ) {
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword( String confirmPassword ) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "UnicornDTO{" +
		       "email='" + email + '\'' +
		       ", password='" + password + '\'' +
		       ", confirmPassword='" + confirmPassword + '\'' +
		       '}';
	}
}
