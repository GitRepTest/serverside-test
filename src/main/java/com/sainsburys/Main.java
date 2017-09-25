package com.sainsburys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sainsburys.entity.Product;
import com.sainsburys.tools.PageParser;
import com.sainsburys.tools.PageScraper;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.util.List;


public class Main {
    private static final String DEFAULT_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    public static void main(String[] args) {
        System.out.println("Application running");
        PageScraper pageScraper = new PageScraper();
        String pageContent = pageScraper.getPageSource(DEFAULT_URL);

        PageParser pageParser = new PageParser(pageContent);
        String productsCssSelector = ".productLister.gridView .gridItem";
        Elements productElements = pageParser.getItems(productsCssSelector);

        List<String> productsUrl=pageParser.getUrls("a",productElements);
        System.out.println(String.format("%s urls found!",productsUrl.size()));

        List<String> productsList = pageScraper.getPageSources(productsUrl);
        List<Product> products = pageParser.getProductDetails(productsList);
        System.out.println(String.format("%s products found!",products.size()));

        double unitPriceTotal=0;
        for(Product product:products){
            unitPriceTotal+=Double.parseDouble(product.getUnitPrice());
        }
        unitPriceTotal= Double.parseDouble(new DecimalFormat("#0.00").format(unitPriceTotal));
        System.out.println(String.format("The total is %s!",unitPriceTotal));

        ObjectMapper mapper = new ObjectMapper();


        ObjectNode node = mapper.createObjectNode();
        ArrayNode nodeArray = mapper.valueToTree(products);
        node.putArray("results").addAll(nodeArray);
        node.put("total",unitPriceTotal);
        System.out.println(node);


    }



}