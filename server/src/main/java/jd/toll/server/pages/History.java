package jd.toll.server.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;


public class History extends WebPage {
    public History() {
        add(new Label("history", "This is History!!!!"));
    }
}