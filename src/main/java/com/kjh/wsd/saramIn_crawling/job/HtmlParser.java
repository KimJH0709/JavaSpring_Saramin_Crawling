package com.kjh.wsd.saramIn_crawling.job;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlParser {

    public static Document connectToUrl(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(5000)
                .get();
    }

    public static Elements selectElements(Document document, String cssQuery) {
        return document.select(cssQuery);
    }
}
