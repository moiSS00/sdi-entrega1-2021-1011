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
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.repositories.UsersRepository;
import com.uniovi.services.InsertSampleDataService;
import com.uniovi.tests.pageobjects.PO_AddOfferView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_SearchListView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

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
		PO_NavView.logOut(driver);
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

		// Password vacía
		PO_RegisterView.fillForm(driver, "pueba@email.com", "prueba", "prueba", "", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Este campo no se puede dejar vacío.");

		// Password confirm vacía
		PO_RegisterView.fillForm(driver, "pueba@email.com", "prueba", "prueba", "123456", "");
		SeleniumUtils.textoPresentePagina(driver, "Este campo no se puede dejar vacío.");
	}

	// PR03.Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida).
	@Test
	public void PR03() {

		// Vamos al formulario de registro
		PO_NavView.goToSignUp(driver);

		// Rellenamos el formulario con contraseñas distitnas
		PO_RegisterView.fillForm(driver, "pueba@email.com", "prueba", "prueba", "123456", "1234567");
		SeleniumUtils.textoPresentePagina(driver, "Las contraseñas deben coincidir.");
	}

	// PR04.Registro de Usuario con datos inválidos (email existente).
	@Test
	public void PR04() {

		// Vamos al formulario de registro
		PO_NavView.goToSignUp(driver);

		// Rellenamos el formulario con un email ya existente
		PO_RegisterView.fillForm(driver, "correo1@email.com", "prueba", "prueba", "123456", "123456");
		SeleniumUtils.textoPresentePagina(driver, "Ya existe un usuario con este email.");
	}

	// PR05.Inicio de sesión con datos válidos (administrador).
	@Test
	public void PR05() {

		// Iniciamos sesión como administrador
		PO_NavView.logInAs(driver, "admin@email.com", "admin");
		PO_View.checkElement(driver, "text", "admin@email.com");

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR06.Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void PR06() {

		// Introducimos los datos de un usuario estandar
		PO_NavView.logInAs(driver, "correo4@email.com", "1234567");
		PO_View.checkElement(driver, "text", "correo4@email.com");

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR07.Inicio de sesión con datos inválidos (usuario estándar, campo email y
	// contraseña vacíos).
	@Test
	public void PR07() {

		// Dejamos ambos campos vacíos y comprobamos que se despliega el mensaje de
		// error
		// y segimos en el formulario de login.
		PO_NavView.logInAs(driver, "", "");		
		List<WebElement> elements = PO_View.checkElement(driver, "class", "alert");
		assertTrue(elements.size() == 1);
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// PR08.Inicio de sesión con datos válidos (usuario estándar, email existente,
	// pero contraseña incorrecta).
	@Test
	public void PR08() {

		// Utilizamos un email existente con una contraseña y comprobamos que se
		// despliega el mensaje de error y segimos en el formulario de login.
		PO_NavView.logInAs(driver, "correo4@email.com", "incorrecto");		
		List<WebElement> elements = PO_View.checkElement(driver, "class", "alert");
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

		// Iniciamos sesión como usuario estandar.
		PO_NavView.logInAs(driver, "correo4@email.com", "1234567");
		
		// Hacemos logout
		PO_NavView.logOut(driver);

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

		// Iniciamos sesión como administrador.
		PO_NavView.logInAs(driver, "admin@email.com", "admin");

		// Ir a la lista de usuarios
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/user/list");
		elements.get(0).click();

		// Comprobamos que figuran todos los usuarios del sistema (Excepto el usuarios
		// administrador,
		// debido a las consideraciones importantes del requsito obligatorio 5)
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableUsers\"]/tbody/tr");
		assertTrue(elements.size() == 5);

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR13. Ir a la lista de usuarios, borrar el primer usuario de la lista,
	// comprobar que la lista se actualiza y que el usuario desaparece.
	@Test
	public void PR13() {

		// Iniciamos sesión como administrador.
		PO_NavView.logInAs(driver, "admin@email.com", "admin");

		// Ir a la lista de usuarios
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/user/list");
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
		PO_NavView.logOut(driver);
	}

	// PR14. Ir a la lista de usuarios, borrar el último usuario de la lista,
	// comprobar que la lista se actualiza y que el usuario desaparece.
	@Test
	public void PR14() {

		// Iniciamos sesión como administrador.
		PO_NavView.logInAs(driver, "admin@email.com", "admin");

		// Ir a la lista de usuarios
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/user/list");
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
		PO_NavView.logOut(driver);
	}

	// PR15. Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se
	// actualiza y que los usuarios desaparecen.
	@Test
	public void PR15() {

		// Iniciamos sesión como administrador.
		PO_NavView.logInAs(driver, "admin@email.com", "admin");

		// Ir a la lista de usuarios
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/user/list");
		elements.get(0).click();

		// Seleccionamos el primer y ultimo usuario. Tambien seleccionamos uno
		// intermedio
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
		PO_NavView.logOut(driver);
	}

	// PR16. Ir al formulario de alta de oferta, rellenarla con datos válidos y
	// pulsar el botón Submit. Comprobar que la oferta sale en el listado de ofertas
	// de dicho usuario.
	@Test
	public void PR16() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de dar de alta una nota
		PO_NavView.goToaddOffer(driver);

		// Rellenamos el formulario de alta de oferta con datos validos
		PO_AddOfferView.fillForm(driver, "PruebaTitulo", "PruebaDescripcion", "0.21");

		// Comprobamos que la oferta recien añadida sale en la lista de ofertas propias
		// del usuario
		PO_View.checkElement(driver, "text", "PruebaTitulo");
		PO_View.checkElement(driver, "text", "PruebaDescripcion");
		PO_View.checkElement(driver, "text", "0.21");

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR17. Ir al formulario de alta de oferta, rellenarla con datos inválidos
	// (campo título vacío) y pulsar el botón Submit. Comprobar que se muestra el
	// mensaje de campo obligatorio.
	@Test
	public void PR17() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de dar de alta una nota
		PO_NavView.goToaddOffer(driver);

		// Título vacío
		PO_AddOfferView.fillForm(driver, "", "PruebaDescripcion", "0.21");
		SeleniumUtils.textoPresentePagina(driver, "Este campo no se puede dejar vacío.");

		// Descripción vacía
		PO_AddOfferView.fillForm(driver, "PruebaTitulo", "", "0.21");
		SeleniumUtils.textoPresentePagina(driver, "Este campo no se puede dejar vacío.");

		// Precio vacío
		PO_AddOfferView.fillForm(driver, "PruebaTitulo", "PruebaDescripcion", "");
		SeleniumUtils.textoPresentePagina(driver, "Este campo no se puede dejar vacío.");

		// Título demasiado corto
		PO_AddOfferView.fillForm(driver, "Pru", "PruebaDescripcion", "0.21");
		SeleniumUtils.textoPresentePagina(driver,
				"El título debe de tener una longitud mínima de 5 carácteres y una longitud máxima de 20 carácteres.");

		// Descripción demasiado corta
		PO_AddOfferView.fillForm(driver, "PruebaTitulo", "Pru", "0.21");
		SeleniumUtils.textoPresentePagina(driver,
				"La descripción debe de tener una longitud mínima de 5 carácteres y una longitud máxima de 50 carácteres.");

		// Precio negativo
		PO_AddOfferView.fillForm(driver, "PruebaTitulo", "PruebaDescripcion", "-0.21");
		SeleniumUtils.textoPresentePagina(driver, "El precio debe de ser un valor positivo.");

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR18. Mostrar el listado de ofertas para dicho usuario y comprobar que se
	// muestran todas los que existen para este usuario.
	@Test
	public void PR18() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de listar ofertas propias
		PO_NavView.goToOwnOffers(driver);

		// Comprobamos que estan todas las ofertas
		List<WebElement> elements = PO_View.checkElement(driver, "free", "//*[@id=\"offersTable\"]/tbody/tr");
		assertTrue(elements.size() == 3);

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR19. Ir a la lista de ofertas, borrar la primera oferta de la lista,
	// comprobar que la lista se actualiza y que la oferta desaparece
	@Test
	public void PR19() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de listar ofertas propias
		PO_NavView.goToOwnOffers(driver);

		// Seleccionar la primera oferta
		List<WebElement> elements = PO_View.checkElement(driver, "free", "//*[@id=\"offersTable\"]/tbody/tr/td[4]/a");
		assertTrue(elements.size() == 3);
		elements.get(0).click();

		// Comprobar que se ha eliminado correctamente
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"offersTable\"]/tbody/tr");
		assertTrue(elements.size() == 2);
		SeleniumUtils.textoNoPresentePagina(driver, "Coche SEAT");

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR20. Ir a la lista de ofertas, borrar la última oferta de la lista,
	// comprobar que la lista se actualiza y
	// que la oferta desaparece
	@Test
	public void PR20() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de listar ofertas propias
		PO_NavView.goToOwnOffers(driver);

		// Seleccionar la primera oferta
		List<WebElement> elements = PO_View.checkElement(driver, "free", "//*[@id=\"offersTable\"]/tbody/tr/td[4]/a");
		assertTrue(elements.size() == 3);
		elements.get(elements.size() - 1).click();

		// Comprobar que se ha eliminado correctamente
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"offersTable\"]/tbody/tr");
		assertTrue(elements.size() == 2);
		SeleniumUtils.textoNoPresentePagina(driver, "Portatil");

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR21. Hacer una búsqueda con el campo vacío y comprobar que se muestra la
	// página que corresponde con el listado de las ofertas existentes en el sistema
	@Test
	public void PR21() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de buscar ofertas para comprar
		PO_NavView.goToSearchOffer(driver);

		// Realizamos una búsqueda vacía
		PO_SearchListView.makeSearch(driver, "");

		// Comprobar que el contenido de cada página es el correcto
		List<WebElement> elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableSearchedOffers\"]/tbody/tr");
		assertTrue(elements.size() == 5);
		elements = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elements.get(2).click();
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableSearchedOffers\"]/tbody/tr");
		assertTrue(elements.size() == 5);
		elements = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elements.get(3).click();
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableSearchedOffers\"]/tbody/tr");
		assertTrue(elements.size() == 2);

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR22. Hacer una búsqueda escribiendo en el campo un texto que no exista y
	// comprobar que se muestra la página que corresponde, con la lista de ofertas
	// vacía
	@Test
	public void PR22() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de buscar ofertas para comprar
		PO_NavView.goToSearchOffer(driver);

		// Realizamos una búsqueda con un texto que no exista
		PO_SearchListView.makeSearch(driver, "prueba");

		// Comprobar que el contenido de cada página es el correcto
		List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"tableSearchedOffers\"]/tbody/tr"));
		assertTrue(elements.size() == 0);
		elements = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		assertTrue(elements.size() == 3);

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR23. Sobre una búsqueda determinada (a elección del desarrollador), comprar
	// una oferta que deja un saldo positivo en el contador del comprador. Comprobar
	// que el contador se actualiza correctamente en la vista del comprador.
	@Test
	public void PR23() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de buscar ofertas para comprar
		PO_NavView.goToSearchOffer(driver);

		// Realizamos una búsqueda
		PO_SearchListView.makeSearch(driver, "cula");

		// Compramos un producto que nos deja en saldo positivo
		List<WebElement> elements = PO_View.checkElement(driver, "text", "100.0");
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"tableSearchedOffers\"]/tbody/tr[1]/td[4]/div/div/a");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Comprobamos que se actualizó el saldo y que consta como vendida
		elements = PO_View.checkElement(driver, "text", "97.7");
		PO_View.checkElement(driver, "free", "//*[@id=\"tableSearchedOffers\"]/tbody/tr[1]/td[4]/div/div/span");

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR24. Sobre una búsqueda determinada (a elección del desarrollador), comprar
	// una oferta que deja un saldo 0 en el contador del comprador. Comprobar que el
	// contador se actualiza correctamente en la vista del comprador.
	@Test
	public void PR24() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de buscar ofertas para comprar
		PO_NavView.goToSearchOffer(driver);


		// Realizamos una búsqueda
		PO_SearchListView.makeSearch(driver, "disco duro");


		// Comprobar un producto que nos deja con saldo 0
		List<WebElement> elements = PO_View.checkElement(driver, "text", "100.0");
		elements = PO_View.checkElement(driver, "text", "Comprar");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Comprobamos que se actualizó el saldo y que consta como vendida
		elements = PO_View.checkElement(driver, "text", "0.0");
		PO_View.checkElement(driver, "text", "Vendido");

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR25. Sobre una búsqueda determinada (a elección del desarrollador), intentar
	// comprar una oferta que esté por encima de saldo disponible del comprador. Y
	// comprobar que se muestra el mensaje de saldo no suficiente.
	@Test
	public void PR25() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de buscar ofertas para comprar
		PO_NavView.goToSearchOffer(driver);

		// Realizamos una búsqueda
		PO_SearchListView.makeSearch(driver, "Ordenador fijo personalizado");

		// Intentamos comprar un producto con un precio superior a nuestro saldo
		List<WebElement> elements = PO_View.checkElement(driver, "text", "100.0");
		elements = PO_View.checkElement(driver, "text", "Comprar");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Comprobamos que sale el anuncio de error y que no se ve afectado el saldo del
		// usuario
		elements = PO_View.checkElement(driver, "class", "alert");
		assertTrue(elements.size() == 1);
		elements = PO_View.checkElement(driver, "text", "100.0");
		elements = PO_View.checkElement(driver, "text", "Comprar");
		assertTrue(elements.size() == 1);

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR26. Ir a la opción de ofertas compradas del usuario y mostrar la lista.
	// Comprobar que aparecen las ofertas que deben aparecer.
	@Test
	public void PR26() {


		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Ir a la opcion de buscar ofertas para comprar
		List<WebElement> elements = PO_View.checkElement(driver, "id", "offers-menu");
		elements.get(0).click();
		elements = PO_View.checkElement(driver, "@href", "/offer/purchasedList");
		elements.get(0).click();

		// Realizamos una búsqueda
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"offersTable\"]/tbody/tr");
		assertTrue(elements.size() == 2);
		PO_View.checkElement(driver, "text", "Ordenador fijo HP");
		PO_View.checkElement(driver, "text", "correo2@email.com");
		PO_View.checkElement(driver, "text", "Televisión 4K");
		PO_View.checkElement(driver, "text", "correo3@email.com");

		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR27. Visualizar al menos cuatro páginas haciendo el cambio
	// español/inglés/español
	// (comprobando que algunas de las etiquetas cambian al idioma correspondiente).
	// Página principal/Opciones principales de usuario/Listado de usuarios /Vista
	// de alta de oferta.
	@Test
	public void PR27() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");
		
		// Probamos que el mensaje de bienvenida al usuario se cambia de idioma 
		// correctamente 
		PO_NavView.changeIdiom(driver, "Spanish");
		SeleniumUtils.textoPresentePagina(driver, "¡ Bienvenido !");
		PO_NavView.changeIdiom(driver, "English");
		SeleniumUtils.textoPresentePagina(driver, "Welcome !");
		PO_NavView.changeIdiom(driver, "Spanish");
		SeleniumUtils.textoPresentePagina(driver, "¡ Bienvenido !");
		
		//Tambien provaremos con una opción del menú		
		PO_NavView.changeIdiom(driver, "Spanish");
		SeleniumUtils.textoPresentePagina(driver, "Idioma");
		PO_NavView.changeIdiom(driver, "English");
		SeleniumUtils.textoPresentePagina(driver, "Language");
		PO_NavView.changeIdiom(driver, "Spanish");
		SeleniumUtils.textoPresentePagina(driver, "Idioma");
		
		//Vamos a la vista de dar de alta una oferta para hacer las mismas comprobaciones
		List<WebElement> elements = PO_View.checkElement(driver, "id", "offers-menu");
		elements.get(0).click(); 
		elements = PO_View.checkElement(driver, "@href", "offer/add");
		elements.get(0).click();
		PO_NavView.changeIdiom(driver, "Spanish");
		SeleniumUtils.textoPresentePagina(driver, "Dar de alta una oferta");
		PO_NavView.changeIdiom(driver, "English");
		SeleniumUtils.textoPresentePagina(driver, "Register an offer");
		PO_NavView.changeIdiom(driver, "Spanish");
		SeleniumUtils.textoPresentePagina(driver, "Dar de alta una oferta");


		// Hacemos logout
		PO_NavView.logOut(driver);
	}

	// PR28. Intentar acceder sin estar autenticado a la opción de listado de
	// usuarios del administrador. Se deberá volver al formulario de login.
	@Test
	public void PR28() {

		// Intentamos acceder a la lista de usuarios sin estar autenticado
		driver.navigate().to(URL + "/user/list");

		// Comprobamos que nos devuelve al formulario de login
		PO_View.checkElement(driver, "text", "Identifícate");

	}

	// PR29. Intentar acceder sin estar autenticado a la opción de listado de
	// ofertas propias de un usuario estándar. Se deberá volver al formulario de
	// login.
	@Test
	public void PR29() {

		// Intentamos acceder a la lista de ofertas propias del usuario usuarios sin
		// estar autenticado
		driver.navigate().to(URL + "/offer/ownedList");

		// Comprobamos que nos devuelve al formulario de login
		PO_View.checkElement(driver, "text", "Identifícate");

	}

	// PR30. Estando autenticado como usuario estándar intentar acceder a la opción
	// de listado de usuarios del administrador. Se deberá indicar un mensaje de
	// acción prohibida.
	@Test
	public void PR30() {

		// Iniciamos sesión como usuario estandar
		PO_NavView.logInAs(driver, "correo1@email.com", "1234567");

		// Intentamos acceder a la lista de usuarios sim ser adiminitrador
		driver.navigate().to(URL + "/user/list");

		// Comprobamos que nos devuelve al formulario de login
		SeleniumUtils.textoPresentePagina(driver, "Forbidden");

		// Volvemos al comienzo para cerrar sesión
		driver.navigate().to(URL + "/home");
		
		PO_NavView.logOut(driver);
	}

}
