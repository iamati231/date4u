package com.tutego.date4u.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.List;

public class SearchPage extends Base {

	@FindBy( className = "fa-search" )
	private WebElement searchLinkElement;

	@FindBy( id = "gender" )
	private WebElement genderElement;

	@FindBy( xpath = "//button[contains(text(),'Suchen')]" )
	private WebElement searchButtonElement;

	@FindBy( tagName = "tr" )
	private List<WebElement> resultElement;

	public SearchPage( WebDriver driver ) {
		super( driver );
	}

	public void clickSearchLink() {
		click( searchLinkElement, Duration.ofSeconds( 2 ) );
	}

	public void selectGender() {
		select( genderElement, Duration.ofSeconds( 2 ), "Mann" );
	}

	public void clickSearchButton() {
		click( searchButtonElement, Duration.ofSeconds( 2 ) );
	}

	public int getResultElement() {
		return resultElement.size();
	}
}