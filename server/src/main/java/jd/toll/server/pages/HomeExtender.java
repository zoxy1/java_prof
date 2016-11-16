package jd.toll.server.pages;

import org.apache.wicket.markup.html.basic.Label;

/**
 * Created by saturn on 18.10.2016.
 */
public class HomeExtender extends Home {
    public HomeExtender() {
        add(new Label("label-extend", "This is label from extender!!!!"));
    }
}
