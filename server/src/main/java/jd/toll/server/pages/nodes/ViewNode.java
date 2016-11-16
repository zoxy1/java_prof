package jd.toll.server.pages.nodes;

import jd.toll.server.WicketApplication;
import jd.toll.server.domain.XBeeNode;
import jd.toll.server.pages.examples.ExamplePage;
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.util.string.StringValue;

/**
 * Created by saturn on 04.10.2015.
 */
//@MountPath ("/layers/viewstore")
public class ViewNode extends ExamplePage {

    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (getPageParameters().isEmpty()) {
            setResponsePage(AllNodesPage.class);
            return;
        }
        StringValue name = getPageParameters().get("name");
        if (name.isEmpty()) {
            setResponsePage(AllNodesPage.class);
            return;
        }

        WicketApplication app = (WicketApplication) Application.get();
        XBeeNode store = app.getNodeService().getNode(name.toString());

        if (store == null) {
            setResponsePage(AllNodesPage.class);
            return;

        }

        StringValue isNew = getPageParameters().get("new");
        if (!isNew.isEmpty()) {
            info("новый модуль сохранен : " + store.getZigBeeId());
        }

        FilterModel fm = new FilterModel(store);

        add(new Label("name", fm.getStore_name()));
        add(new ViewPanel("view_panel", fm));
    }

}
