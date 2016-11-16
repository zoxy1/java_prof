package jd.toll.server.pages;


import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class View extends WebPage {
    public View() {
        add(new Label("view", "This is label from view!!!!"));
    }

}

