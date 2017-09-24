package com.sainsburys.tools;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.List;

public class PageScraper {

    String getPageSource(String url) {
        WebDriver driver = new HtmlUnitDriver();
        driver.get(url);
        return driver.getPageSource();
    }

}
