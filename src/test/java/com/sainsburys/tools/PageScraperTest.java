package com.sainsburys.tools;

import com.sainsburys.entity.Product;
import org.hamcrest.CoreMatchers;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PageScraperTest {
    private final String DEFAULT_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    PageScraper pageScraper;

    @Before
    public void setup(){
        pageScraper=new PageScraper();
    }

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
    public void checkIfProductDetailsAreValid(){
        Product exPectedProduct = buildMockProduct();

        String pageContent = getPageSource(DEFAULT_URL);
        Elements elements = getElements(pageContent);
        PageParser pageParser = new PageParser(pageContent);

        List<String> urls = pageParser.getUrls("a",elements);
        List<String> productsList = pageScraper.getPageSources(urls);
        List<Product> products = pageParser.getProductDetails(productsList);
        Product actualProduct=products.get(0);
        assertEquals(exPectedProduct.getTitle(),actualProduct.getTitle());
        assertEquals(exPectedProduct.getDescription(),actualProduct.getDescription());
        assertEquals(exPectedProduct.getKcalPer100g(),actualProduct.getKcalPer100g());

        assertEquals(exPectedProduct.getUnitPrice(),actualProduct.getUnitPrice());

    }

    private Product buildMockProduct() {
        Product exPectedProduct = new Product();
        exPectedProduct.setTitle("Sainsbury's Strawberries 400g");
        exPectedProduct.setDescription("by Sainsbury's strawberries");
        exPectedProduct.setKcalPer100g("33");
        exPectedProduct.setUnitPrice("1.75");
        return exPectedProduct;
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