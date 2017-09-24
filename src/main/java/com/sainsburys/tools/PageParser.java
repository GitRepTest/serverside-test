package com.sainsburys.tools;


import com.sainsburys.entity.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class PageParser {

    private String pageSource;
    private final String PREFIX_URL="https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk";

    public PageParser(String pageSource){
        this.pageSource=pageSource;
    }

    Elements getItems(String cssSelector){
        Document doc = Jsoup.parse(this.pageSource);
        return doc.select(cssSelector);
    }

    public List<String> getUrls(String cssSelector, Elements elements){
        List<String> urls = new ArrayList<String>();
        for(Element url:elements){
            urls.add(cleanUrl(url.select(cssSelector).first().attr("href")));
        }
        return urls;
    }

    public Product getProductDetails(String productSource){
        Product product = new Product();
        Document doc = Jsoup.parse(productSource);
        String kcal = getCaloriesPer100g(doc);
        product.setKcalPer100g(kcal);

        String description = doc.select(".productText p").first().text();
        product.setDescription(description);

        String title = doc.select(".productTitleDescriptionContainer h1").text();
        product.setTitle(title);

        String unitPrice = getPricePerUnit(doc);
        product.setUnitPrice(unitPrice);
        return product;
    }

    public List<Product> getProductDetails(List<String> productList){
        List<Product> products = new ArrayList<Product>();
        for(String product:productList){
            products.add(getProductDetails(product));
        }
        return products;
    }

    private String getPricePerUnit(Document doc) {
        return doc.select(".pricePerUnit").first().text().replaceAll("\\/ unit","").replaceAll("£","").trim();
    }

    private String getCaloriesPer100g(Document doc) {
        String kcal;
        if(doc.select(".nutritionTable").size()!=0) {
            if (doc.select(".nutritionTable .tableRow0 td").size() > 0) {
                kcal = doc.select(".nutritionTable .tableRow0 td").first().text();
                kcal = kcal.substring(0, kcal.indexOf("kcal"));
            }{
                kcal = doc.select(".nutritionTable tr").get(2).select("td").first().text();
            }
        }else{
            kcal="";
        }
        return kcal;
    }


    private String cleanUrl(String url){
        url= PREFIX_URL+url.substring(url.indexOf("/shop"),url.length());
        return url;
    }
}
