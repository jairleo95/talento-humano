package com.app.contract.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;

public final class HtmlSanitizer {

    private static final Safelist SAFE_TAGS = Safelist.relaxed()
            .addTags("section", "div", "span", "table", "thead", "tbody", "tr", "th", "td", "caption")
            .addAttributes("table", "border", "cellpadding", "cellspacing", "width")
            .addAttributes("th", "td", "colspan", "rowspan", "width", "align")
            .addAttributes(":all", "style")
            .addProtocols("a", "href", "http", "https", "mailto");

    private HtmlSanitizer() {
    }

    public static String sanitize(String html) {
        if (html == null || html.isBlank()) {
            return html;
        }
        return stripDangerousCss(Jsoup.clean(html, SAFE_TAGS));
    }

    public static String escapeText(String value) {
        if (value == null) {
            return "—";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static String stripDangerousCss(String html) {
        Document document = Jsoup.parseBodyFragment(html);
        for (Element element : document.select("[style]")) {
            String style = element.attr("style");
            if (style == null || style.toLowerCase().contains("url(")
                    || style.toLowerCase().contains("expression(")
                    || style.toLowerCase().contains("@import")) {
                element.removeAttr("style");
            }
        }
        return document.body().html();
    }
}