package jd.toll.server.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Created by saturn on 17.10.2016.
 */
public class Logout extends WebPage {

    public Logout() {
        add(new Label("label", "This is logout!!!!"));
    }

}

