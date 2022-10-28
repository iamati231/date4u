package com.tutego.date4u.testcases;

import com.tutego.date4u.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;

@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public class LoginTest {

	Logger log = LoggerFactory.getLogger( LoginTest.class );

	@Value( "${date4u.email}" )
	private String date4uEmail;

	@Value( "${date4u.password}" )
	private String date4uPassword;

	@Autowired
	private WebDriver driver;
	@Autowired
	private LoginPage loginPage;

	@BeforeEach
	public void setUp() {
		driver = new ChromeDriver();
		loginPage = new LoginPage( driver );

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait( Duration.ofSeconds( 5 ) );

		loginPage.typeEmail( "test@test.de" );
		loginPage.typePassword( "1234" );
		loginPage.clickLoginButton();

		log.info( "Test started" );
	}

	@AfterEach
	public void tearDown() {
		driver.quit();

		log.info( "Test completed" );
	}

	@Test
	public void testSuccessfulLogin() {
		Assertions.assertTrue( loginPage.isLoggedIn() );
	}

	@Test
	public void testSuccessfulLogout() {
		loginPage.clickLogoutButton();

		Assertions.assertTrue( loginPage.isLoggedOut() );
	}

}
