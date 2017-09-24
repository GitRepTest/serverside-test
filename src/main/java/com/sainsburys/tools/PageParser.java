package com.sainsburys.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tech on 24/09/2017.
 */
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




    private String cleanUrl(String url){
        url= PREFIX_URL+url.substring(url.indexOf("/shop"),url.length());
        return url;
    }
}
