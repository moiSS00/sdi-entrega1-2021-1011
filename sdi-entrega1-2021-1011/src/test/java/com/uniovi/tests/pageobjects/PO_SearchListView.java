package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_SearchListView extends PO_View {
	
	/**
	 * Realiza una búsqueda con la cadena indicada
	 * @param driver
	 * @param searchText
	 */
	public static void makeSearch(WebDriver driver, String searchText) {
		List<WebElement> elements = PO_View.checkElement(driver, "free", "//*[@id=\"searchTextForm\"]/div/input");
		assertTrue(elements.size() == 1);
		elements.get(0).click();
		elements.get(0).sendKeys(searchText);
		elements = PO_View.checkElement(driver, "free", "//*[@id=\"searchTextForm\"]/button");
		elements.get(0).click();
	}

}
