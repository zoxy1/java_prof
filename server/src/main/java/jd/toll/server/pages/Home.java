package jd.toll.server.pages;

import jd.toll.server.WicketApplication;
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import java.util.List;

/**
 * Created by saturn on 17.10.2016.
 */
public class Home extends WebPage {

    public Home() {
        WicketApplication app = (WicketApplication) Application.get();
        add(new Label("label", "The number of nodes is " + ((List)app.getNodeRepository().findAll()).size()));
    }

}
