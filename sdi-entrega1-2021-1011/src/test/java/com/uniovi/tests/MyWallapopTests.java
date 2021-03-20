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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.repositories.UsersRepository;
import com.uniovi.services.InsertSampleDataService;
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

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private InsertSampleDataService insertSampleDataService;

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

	// Antes de cada prueba se navega al URL home de la aplicación y se
	// reinicia la base de datos
	@Before
	public void setUp() {
		driver.navigate().to(URL);
		initb();
	}

	// Reinicia la base de datos
	public void initb() {
		// Borramos todas las entidades
		usersRepository.deleteAll();

		// Metemos otra vez los datos iniciales de prueba
		insertSampleDataService.init();
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
		PO_RegisterView.fillForm(driver, "pueba@email.com", "prueba", "prueba", "123456", "123456");
		PO_View.checkElement(driver, "text", "pueba@email.com");

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
		PO_RegisterView.fillForm(driver, "pueba@email.com", "", "prueba", "123456", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Este campo no se puede dejar vacío.");

		// Apellidos vacíos
		PO_RegisterView.fillForm(driver, "pueba@email.com", "prueba", "", "123456", "123456");
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
		PO_RegisterView.fillForm(driver, "pueba@email.com", "prueba", "prueba", "123456", "1234567");
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
		PO_RegisterView.fillForm(driver, "correo1@email.com", "prueba", "prueba", "123456", "123456");
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
		PO_LoginView.fillForm(driver, "correo4@email.com", "1234567");
		PO_View.checkElement(driver, "text", "correo4@email.com");

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

		// Dejamos ambos campos vacíos y comprobamos que se despliega el mensaje de
		// error
		// y segimos en el formulario de login.
		PO_LoginView.fillForm(driver, "", "");
		elements = PO_View.checkElement(driver, "class", "alert");
		assertTrue(elements.size() == 1);
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// PR08.Inicio de sesión con datos válidos (usuario estándar, email existente,
	// pero contraseña incorrecta).
	@Test
	public void PR08() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Utilizamos un email existente con una contraseña y comprobamos que se
		// despliega el mensaje de error y segimos en el formulario de login.
		PO_LoginView.fillForm(driver, "correo4@email.com", "incorrecto");
		elements = PO_View.checkElement(driver, "class", "alert");
		assertTrue(elements.size() == 1);
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// PR09.Inicio de sesión con datos inválidos (usuario estándar, email no
	// existente en la aplicación).
	@Test
	public void PR09() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Utilizamos un email no existente en la aplicación y comprobamos que se
		// despliega el mensaje de error y segimos en el formulario de login.
		PO_LoginView.fillForm(driver, "correo6@prueba.com", "1234567");
		elements = PO_View.checkElement(driver, "class", "alert");
		assertTrue(elements.size() == 1);
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// PR10. Hacer click en la opción de salir de sesión y comprobar que se
	// redirige a la página de inicio de sesión (Login).
	@Test
	public void PR10() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Iniciamos sesión como usuario estandar.
		PO_LoginView.fillForm(driver, "correo4@email.com", "1234567");

		// Hacemos logout
		elements = PO_View.checkElement(driver, "@href", "/logout");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Comprobamos que estamos en la página de inicio
		PO_View.checkElement(driver, "text", "¡ Bienvenidos a MyWallapop !");
	}

	// PR11. Comprobar que el botón cerrar sesión no está visible si el usuario no
	// está autenticado.
	@Test
	public void PR11() {

		// Comprobamos que el botón de inicio de cerrar sesión no es visible
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
	}

	// PR12. Mostrar el listado de usuarios y comprobar que se muestran todos los
	// que existen en el sistema.
	@Test
	public void PR12() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Iniciamos sesión como administrador.
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		// Ir a la lista de usuarios
		elements = PO_View.checkElement(driver, "@href", "/user/list");
		elements.get(0).click();

		// Comprobamos que figuran todos los usuarios del sistema (Excepto el usuarios
		// administrador,
		// debido a las consideraciones importantes del requsito obligatorio 5)
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr");
		assertTrue(elements.size() == 5);

		// Hacemos logout
		elements = PO_View.checkElement(driver, "@href", "/logout");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
	}

	// PR13. Ir a la lista de usuarios, borrar el primer usuario de la lista,
	// comprobar que la lista se actualiza y que el usuario desaparece.
	@Test
	public void PR13() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Iniciamos sesión como administrador.
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		// Ir a la lista de usuarios
		elements = PO_View.checkElement(driver, "@href", "/user/list");
		elements.get(0).click();

		// Seleccionamos el primer usuario que aparece
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr/td[4]/input");
		assertTrue(elements.size() == 5);
		elements.get(0).click();

		// Damos al botón correspondiente para eliminar a los usuarios seleccionados
		elements = PO_View.checkElement(driver, "class", "btn");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Comprobamos que el usuario ya no figura en la tabla
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr");
		assertTrue(elements.size() == 4);
		SeleniumUtils.textoNoPresentePagina(driver, "correo1@email.com");

		// Hacemos logout
		elements = PO_View.checkElement(driver, "@href", "/logout");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
	}

	// PR14. Ir a la lista de usuarios, borrar el último usuario de la lista,
	// comprobar que la lista se actualiza y que el usuario desaparece.
	@Test
	public void PR14() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Iniciamos sesión como administrador.
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		// Ir a la lista de usuarios
		elements = PO_View.checkElement(driver, "@href", "/user/list");
		elements.get(0).click();

		// Seleccionamos el último usuario que aparece
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr/td[4]/input");
		assertTrue(elements.size() == 5);
		elements.get(elements.size() - 1).click();

		// Damos al botón correspondiente para eliminar a los usuarios seleccionados
		elements = PO_View.checkElement(driver, "class", "btn");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Comprobamos que el usuario ya no figura en la tabla
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr");
		assertTrue(elements.size() == 4);
		SeleniumUtils.textoNoPresentePagina(driver, "correo5@email.com");

		// Hacemos logout
		elements = PO_View.checkElement(driver, "@href", "/logout");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
	}

	// PR15. Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se
	// actualiza y que los usuarios desaparecen.
	@Test
	public void PR15() {

		// Vamos al formulario de inicio de sesion
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Iniciamos sesión como administrador.
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		// Ir a la lista de usuarios
		elements = PO_View.checkElement(driver, "@href", "/user/list");
		elements.get(0).click();

		// Seleccionamos el primer y ultimo usuario. Tambien seleccionamos uno intermedio
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr/td[4]/input");
		assertTrue(elements.size() == 5);
		elements.get(0).click();
		elements.get(2).click();
		elements.get(elements.size() - 1).click();

		// Damos al botón correspondiente para eliminar a los usuarios seleccionados
		elements = PO_View.checkElement(driver, "class", "btn");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Comprobamos que el usuario ya no figura en la tabla
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr");
		assertTrue(elements.size() == 2);
		SeleniumUtils.textoNoPresentePagina(driver, "correo1@email.com");
		SeleniumUtils.textoNoPresentePagina(driver, "correo3@email.com");
		SeleniumUtils.textoNoPresentePagina(driver, "correo5@email.com");

		// Hacemos logout
		elements = PO_View.checkElement(driver, "@href", "/logout");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
	}

}
