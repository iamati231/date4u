package com.tutego.date4u.testcases;

import com.tutego.date4u.pages.LoginAndLogoutPage;
import com.tutego.date4u.pages.SearchPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public class SearchTest {

	Logger log = LoggerFactory.getLogger( SearchTest.class );

	@Autowired
	private WebDriver driver;
	@Autowired
	private LoginAndLogoutPage loginAndLogoutPage;
	@Autowired
	private SearchPage searchPage;

	@BeforeEach
	public void setUp() {
		driver = new ChromeDriver();
		loginAndLogoutPage = new LoginAndLogoutPage( driver );
		searchPage = new SearchPage( driver );

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait( Duration.ofSeconds( 5 ) );

		loginAndLogoutPage.typeEmail( "test@test.de" );
		loginAndLogoutPage.typePassword( "1234" );
		loginAndLogoutPage.clickLoginButton();

		log.info( "Test started" );
	}

	@AfterEach
	public void tearDown() {
		driver.quit();

		log.info( "Test completed" );
	}

	@Test
	public void testSearch() throws InterruptedException {
		searchPage.clickSearchLink();
		searchPage.selectGender();
		searchPage.clickSearchButton();

		Assertions.assertEquals( 4, searchPage.getResultElement() );
	}

}
