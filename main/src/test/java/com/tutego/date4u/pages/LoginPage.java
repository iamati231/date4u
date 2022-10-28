package com.tutego.date4u.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LoginPage extends Base {

	@FindBy( id = "email" )
	private WebElement emailElement;

	@FindBy( id = "password" )
	private WebElement passwordElement;

	@FindBy( id = "login-button" )
	private WebElement submitButtonElement;

	@FindBy( xpath = "//body/div[1]/div[1]/nav[1]/div[1]/div[1]/a[3]" )
	private WebElement checkLoggedInElement;

	@FindBy( xpath = "//body/div[1]/div[1]/nav[1]/div[1]/div[2]/a[1]" )
	private WebElement logoutButtonElement;

	@FindBy( xpath = "//body/div[1]/div[1]/nav[1]/div[1]/div[2]/a[2]" )
	private WebElement checkLoggedOutElement;

	public LoginPage( WebDriver driver ) {
		super( driver );
		visit( "http://localhost:8080/login" );
	}

	public void typeEmail( String username ) {
		type( emailElement, Duration.ofSeconds( 2 ), username );
	}

	public void typePassword( String password ) {
		type( passwordElement, Duration.ofSeconds( 2 ), password );
	}

	public void clickLoginButton() {
		click( submitButtonElement, Duration.ofSeconds( 2 ) );
	}

	public boolean isLoggedIn() {
		return elementIsVisible( checkLoggedInElement, Duration.ofSeconds( 2 ) );
	}

	public void clickLogoutButton() {
		click( logoutButtonElement, Duration.ofSeconds( 2 ) );
	}

	public boolean isLoggedOut() {
		return elementIsVisible( checkLoggedOutElement, Duration.ofSeconds( 2 ) );
	}
}
