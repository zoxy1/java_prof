package jd.toll.server.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Created by cisco on 20.10.2016.
 */
public class Admin extends WebPage{
    public Admin() {
        add(new Label("admin", "This is admin!!!!"));
    }
}
