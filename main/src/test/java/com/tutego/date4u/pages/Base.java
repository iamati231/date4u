package com.tutego.date4u.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Base {

	Logger log = LoggerFactory.getLogger( Base.class );

	private final WebDriver driver;

	public Base( WebDriver driver ) {
		this.driver = driver;
		PageFactory.initElements( driver, this );
	}

	public void visit( String url ) {
		try {
			driver.get( url );
		} catch( WebDriverException webDriverException ) {
			log.error( String.valueOf( webDriverException ) );
			Assertions.fail();
		}
	}

	public void click( WebElement element, Duration duration ) {
		try {
			new WebDriverWait( driver, duration ).until( ExpectedConditions.elementToBeClickable( element ) ).click();
		} catch( WebDriverException webDriverException ) {
			log.error( String.valueOf( webDriverException ) );
			Assertions.fail();
		}
	}

	public void type( WebElement element, Duration duration, String inputText ) {
		try {
			new WebDriverWait( driver, duration ).until( ExpectedConditions.elementToBeClickable( element ) );
			element.clear();
			element.sendKeys( inputText );
		} catch( WebDriverException webDriverException ) {
			log.info( String.valueOf( webDriverException ) );
			Assertions.fail();
		}
	}

	public void waitForElementToBeVisible( WebElement element, Duration duration ) {
		try {
			new WebDriverWait( driver, duration ).until( ExpectedConditions.visibilityOf( element ) );
		} catch( WebDriverException webDriverException ) {
			log.error( String.valueOf( webDriverException ) );
			Assertions.fail();
		}
	}

	public void select( WebElement element, Duration duration, String visibleText ) {
		waitForElementToBeVisible( element, duration );
		Select select = new Select( element );

		try {
			select.selectByVisibleText( visibleText );
		} catch( WebDriverException webDriverException ) {
			log.error( String.valueOf( webDriverException ) );
			Assertions.fail();
		}
	}

	public boolean elementIsVisible( WebElement element, Duration duration ) {
		try {
			new WebDriverWait( driver, duration ).until( ExpectedConditions.visibilityOf( element ) );
		} catch( WebDriverException webDriverException ) {
			log.error( String.valueOf( webDriverException ) );
			Assertions.fail();
			return false;
		}
		return true;
	}
}
