package com.tutego.date4u.testcases;

import com.tutego.date4u.pages.LoginAndLogoutPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;

@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public class LoginAndLogoutTest {

	Logger log = LoggerFactory.getLogger( LoginAndLogoutTest.class );

	@Value( "${date4u.email}" )
	private String date4uEmail;

	@Value( "${date4u.password}" )
	private String date4uPassword;

	@Autowired
	private WebDriver driver;
	@Autowired
	private LoginAndLogoutPage loginAndLogoutPage;

	@BeforeEach
	public void setUp() {
		driver = new ChromeDriver();
		loginAndLogoutPage = new LoginAndLogoutPage( driver );

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
	public void testSuccessfulLogin() {
		Assertions.assertTrue( loginAndLogoutPage.isLoggedIn() );
	}

	@Test
	public void testSuccessfulLogout() {
		loginAndLogoutPage.clickLogoutLink();

		Assertions.assertTrue( loginAndLogoutPage.isLoggedOut() );
	}

}
