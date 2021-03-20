package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyWallapopTests {

	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\Moises\\Desktop\\UNIVERSIDAD\\TERCERO\\"
			+ "2 CUATRIMESTRE\\SDI\\Laboratorio\\PL5\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicación
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// PR01. Registro de usuario con datos validos./
	@Test
	public void PR01() {

		// Vamos al formulario de registro
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/signup");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Rellenamos el formulario con datos validos
		PO_RegisterView.fillForm(driver, "prueba@com", "prueba", "prueba", "123456", "123456");
		PO_View.checkElement(driver, "text", "prueba@com");

		// Hacemos logout
		elements = PO_View.checkElement(driver, "@href", "/logout");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
	}

	// PR02. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos)./
	@Test
	public void PR02() {

		// Vamos al formulario de registro
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/signup");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Email vacío
		PO_RegisterView.fillForm(driver, "", "prueba", "prueba", "123456", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Este campo no se puede dejar vacío.");

		// Nombre vacío
		PO_RegisterView.fillForm(driver, "prueba@com", "", "prueba", "123456", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Este campo no se puede dejar vacío.");

		// Apellidos vacíos
		PO_RegisterView.fillForm(driver, "prueba@com", "prueba", "", "123456", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Este campo no se puede dejar vacío.");

	}

	// PR03.Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida).
	@Test
	public void PR03() {

		// Vamos al formulario de registro
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/signup");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Rellenamos el formulario con contraseñas distitnas
		PO_RegisterView.fillForm(driver, "prueba@com", "prueba", "prueba", "123456", "1234567");
		SeleniumUtils.textoPresentePagina(driver, "Las contraseñas deben coincidir.");
	}

	// PR04.Registro de Usuario con datos inválidos (email existente).
	@Test
	public void PR04() {

		// Vamos al formulario de registro
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/signup");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Rellenamos el formulario con un email ya existente
		PO_RegisterView.fillForm(driver, "correo1@prueba.com", "prueba", "prueba", "123456", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Ya existe un usuario con este email.");
	}

	// PR05.Inicio de sesión con datos válidos (administrador).
	@Test
	public void PR05() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Introducimos los datos de la cuenta de administrador
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_View.checkElement(driver, "text", "admin@email.com");

		// Hacemos logout
		elements = PO_View.checkElement(driver, "@href", "/logout");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
	}

	// PR06.Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void PR06() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Introducimos los datos de la cuenta de administrador
		PO_LoginView.fillForm(driver, "correo1@prueba.com", "1234567");
		PO_View.checkElement(driver, "text", "correo1@prueba.com");

		// Hacemos logout
		elements = PO_View.checkElement(driver, "@href", "/logout");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
	}

	// PR07.Inicio de sesión con datos inválidos (usuario estándar, campo email y
	// contraseña vacíos).
	@Test
	public void PR07() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Dejamos ambos campos vacíos
		PO_LoginView.fillForm(driver, "", "");
		PO_View.checkElement(driver, "text", "Idéntificate");
	}

	// PR08.Inicio de sesión con datos válidos (usuario estándar, email existente,
	// pero contraseña incorrecta).
	@Test
	public void PR08() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Dejamos ambos campos vacíos
		PO_LoginView.fillForm(driver, "correo1@prueba.com", "incorrecto");
		PO_View.checkElement(driver, "text", "Idéntificate");
	}

	// PR09.Inicio de sesión con datos inválidos (usuario estándar, email no
	// existente en la aplicación).
	@Test
	public void PR09() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Dejamos ambos campos vacíos
		PO_LoginView.fillForm(driver, "correo6@prueba.com", "1234567");
		PO_View.checkElement(driver, "text", "Idéntificate");
	}

}
