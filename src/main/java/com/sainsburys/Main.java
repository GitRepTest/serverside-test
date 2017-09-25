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
    private static PageScraper pageScraper = new PageScraper();
    ;
    private static PageParser pageParser;


    public static final String PRODUCTS_CSS_SELECTOR = ".productLister.gridView .gridItem";

    public static void main(String[] args) {

        System.out.println("Application running");
        String pageContent = pageScraper.getPageSource(DEFAULT_URL);

        pageParser = new PageParser(pageContent);
        Elements productElements = pageParser.getItems(PRODUCTS_CSS_SELECTOR);

        List<String> productsUrl = getEachProductUrl(productElements);
        System.out.println(String.format("%s urls found!", productsUrl.size()));

        List<String> productsList = getEachProductSourceDescriptionFromUrl(productsUrl);
        List<Product> products = pageParser.getProductDetails(productsList);
        System.out.println(String.format("%s products found!", products.size()));

        double unitPriceTotal = getUnitPriceTotal(products);
        System.out.println(String.format("The total is %s!", unitPriceTotal));

        ObjectNode results = convertToJson(products, unitPriceTotal);
        System.out.println(results);


    }

    private static ObjectNode convertToJson(List<Product> products, double unitPriceTotal) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode results = mapper.createObjectNode();
        ArrayNode nodeArray = mapper.valueToTree(products);

        results.putArray("results").addAll(nodeArray);
        results.put("total", unitPriceTotal);
        return results;
    }

    private static double getUnitPriceTotal(List<Product> products) {
        double unitPriceTotal = 0;
        for (Product product : products) {
            unitPriceTotal += Double.parseDouble(product.getUnitPrice());
        }
        unitPriceTotal = Double.parseDouble(new DecimalFormat("#0.00").format(unitPriceTotal));
        return unitPriceTotal;
    }

    private static List<String> getEachProductSourceDescriptionFromUrl(List<String> productsUrl) {
        return pageScraper.getPageSources(productsUrl);
    }

    private static List<String> getEachProductUrl(Elements productElements) {
        return pageParser.getUrls("a", productElements);
    }


}