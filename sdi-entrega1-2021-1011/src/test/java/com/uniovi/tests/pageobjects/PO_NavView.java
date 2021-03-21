package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_NavView extends PO_View {
	
	/**
	 * Selecciona el enlace de idioma correspondiente al texto textLanguage
	 * 
	 * @param driver:       apuntando al navegador abierto actualmente.
	 * @param textLanguage: el texto que aparece en el enlace de idioma ("English" o
	 *                      "Spanish")
	 */
	public static void changeIdiom(WebDriver driver, String textLanguage) {
		// clickamos la opción Idioma.
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "btnLanguage", getTimeout());
		elementos.get(0).click();
		// Esperamos a que aparezca el menú de opciones.
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "languageDropdownMenuButton", getTimeout());
		// SeleniumUtils.esperarSegundos(driver, 2);
		// CLickamos la opción Inglés partiendo de la opción Español
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", textLanguage, getTimeout());
		elementos.get(0).click();
	}
	
	/**
	 * Hacemos un logout
	 * @param driver
	 */
	public static void logOut(WebDriver driver) {
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/logout");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
	}
	
	/**
	 * Nos logeamos como un usuario determinado 
	 * @param driver
	 * @param username
	 * @param password
	 */
	public static void logInAs(WebDriver driver, String username, String password) {
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/login");
		assertTrue(elements.size() == 1);
		elements.get(0).click();

		// Introducimos las ceredemciales
		PO_LoginView.fillForm(driver, username, password);
	}
	
	/**
	 * Nos redirige al formulario de registro 
	 * @param driver
	 */
	public static void goToSignUp(WebDriver driver) {
		List<WebElement> elements = PO_View.checkElement(driver, "@href", "/signup");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
	}
	
	
	/**
	 * Vamos a la lista para añadir una oferta 
	 * @param driver
	 */
	public static void goToaddOffer(WebDriver driver) {
		List<WebElement> elements = PO_View.checkElement(driver, "id", "offers-menu");
		elements.get(0).click();
		elements = PO_View.checkElement(driver, "@href", "/offer/add");
		elements.get(0).click();
	}

	/**
	 * Vamos a la lista para buscar ofertas  
	 * @param driver
	 */
	public static void goToSearchOffer(WebDriver driver) {
		List<WebElement> elements = PO_View.checkElement(driver, "id", "offers-menu");
		elements.get(0).click();
		elements = PO_View.checkElement(driver, "@href", "/offer/searchList");
		elements.get(0).click();
	}
	
	/**
	 * Vamos a la lista para ver ofertas propias  
	 * @param driver
	 */
	public static void goToOwnOffers(WebDriver driver) {
		List<WebElement> elements = PO_View.checkElement(driver, "id", "offers-menu");
		elements.get(0).click();
		elements = PO_View.checkElement(driver, "@href", "/offer/ownedList");
		elements.get(0).click();
	}
}
