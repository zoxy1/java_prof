/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jd.toll.server.pages.nodes;

import jd.toll.server.WicketApplication;
import jd.toll.server.domain.XBeeNode;
import jd.toll.server.services.NodeService;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Locale;


/**
 * Example for form input.
 * 
 * @author Eelco Hillenius
 * @author Jonathan Locke
 */
public class NodePanel extends Panel
{
    private static final NodeService storeService = ((WicketApplication) Application.get()).getNodeService();

    private final DataView<XBeeNode> dataView;

    public DataView<XBeeNode> getDataView() {
        return dataView;
    }

    public NodePanel(String name, final SortableActionsProvider dp)
	{
        super(name);
        dataView = new DataView<XBeeNode>("sorting", dp)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<XBeeNode> item)
            {
                XBeeNode store = item.getModelObject();
                PageParameters pg = new PageParameters();
                pg.add("name", store.getZigBeeId());
                BookmarkablePageLink link = new BookmarkablePageLink("name", ViewNode.class, pg);
                link.add(new Label("text", store.getZigBeeId()));
                item.add(link);

                item.add(new AjaxLink<Void>("delete") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        System.err.println(getParent());
                        XBeeNode store = (XBeeNode) getParent().getDefaultModelObject();
                        if (store == null) {
                            setResponsePage(AllNodesPage.class);
                            return;
                        }
                        final String name = store.getZigBeeId();
                        storeService.delete(store);
                        setResponsePage(AllNodesPage.class, new PageParameters(){{
                            add("del",name);
                        }});
                    }
                });

                item.add(new Label("address", store.getNodeName()));
                item.add(new Label("coords", store.getLat() + " " + store.getLon()));
                item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
                }));
            }

        };

        dataView.setItemsPerPage(20L);
        dataView.setOutputMarkupId(true);

        add(dataView);
	}

	/**
	 * Sets locale for the user's session (getLocale() is inherited from Component)
	 * 
	 * @param locale
	 *            The new locale
	 */
	public void setLocale(Locale locale)
	{
		if (locale != null)
		{
			getSession().setLocale(locale);
		}
	}
}
