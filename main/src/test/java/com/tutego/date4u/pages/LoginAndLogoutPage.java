package com.tutego.date4u.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LoginAndLogoutPage extends Base {

	@FindBy( id = "email" )
	private WebElement emailInputElement;

	@FindBy( id = "password" )
	private WebElement passwordInputElement;

	@FindBy( id = "login-button" )
	private WebElement loginButtonElement;

	@FindBy( className = "fa-user" )
	private WebElement checkLoggedInElement;

	@FindBy( className = "fa-sign-out" )
	private WebElement logoutLinkElement;

	@FindBy( className = "fa-user-plus" )
	private WebElement checkLoggedOutElement;

	public LoginAndLogoutPage( WebDriver driver ) {
		super( driver );
		visit( "http://localhost:8080/login" );
	}

	public void typeEmail( String username ) {
		type( emailInputElement, Duration.ofSeconds( 2 ), username );
	}

	public void typePassword( String password ) {
		type( passwordInputElement, Duration.ofSeconds( 2 ), password );
	}

	public void clickLoginButton() {
		click( loginButtonElement, Duration.ofSeconds( 2 ) );
	}

	public boolean isLoggedIn() {
		return elementIsVisible( checkLoggedInElement, Duration.ofSeconds( 2 ) );
	}

	public void clickLogoutLink() {
		click( logoutLinkElement, Duration.ofSeconds( 2 ) );
	}

	public boolean isLoggedOut() {
		return elementIsVisible( checkLoggedOutElement, Duration.ofSeconds( 2 ) );
	}
}
