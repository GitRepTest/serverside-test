package com.sainsburys.tools;

import org.hamcrest.CoreMatchers;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PageScraperTest {
    private final String DEFAULT_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
    @Test
    public void checkIfWebsiteReturnsData(){
        String pageContent = getPageSource(DEFAULT_URL);
        Assert.assertThat(pageContent, CoreMatchers.containsString("17 products available"));
    }

    @Test
    public void checkIfProductsQuantityIsValid(){
        String pageContent = getPageSource(DEFAULT_URL);
        Elements items = getElements(pageContent);
        int productsQuantity = items.size();
        assertEquals(17,productsQuantity);
    }

    @Test
    public void getEachProductUrl(){
        String pageContent = getPageSource(DEFAULT_URL);
        Elements products = getElements(pageContent);
        List<String> urls;
        PageParser pageParser = new PageParser(pageContent);
        urls=pageParser.getUrls("a",products);
        assertEquals(17,urls.size());
    }

    @Test
    public void getEachProductDetails(){
        String pageContent = getPageSource(DEFAULT_URL);
        Elements products = getElements(pageContent);
        PageParser pageParser = new PageParser(pageContent);
        List<String> urls = pageParser.getUrls("a",products);
        List<String> pageSources = new ArrayList<String>();
        for(String url:urls){
            pageSources.add(getPageSource(url));
        }
    }

    private Elements getElements(String pageContent) {
        PageParser pageParser = new PageParser(pageContent);
        String productsCssSelector=".productLister.gridView .gridItem";
        return pageParser.getItems(productsCssSelector);
    }
    private String getPageSource(String url) {
        PageScraper pageScraper = new PageScraper();
        return pageScraper.getPageSource(url);
    }
}