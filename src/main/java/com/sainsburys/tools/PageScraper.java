package com.sainsburys.tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.List;


public class PageScraper {
    WebDriver driver;

    public PageScraper() {
        driver = new HtmlUnitDriver();
    }

    public String getPageSource(String url) {
        driver.get(url);
        return driver.getPageSource();
    }

    public List<String> getPageSources(List<String> urls) {
        List<String> pageSources = new ArrayList<String>();
        for (String url : urls) {
            pageSources.add(getPageSource(url));
        }
        return pageSources;
    }
}
