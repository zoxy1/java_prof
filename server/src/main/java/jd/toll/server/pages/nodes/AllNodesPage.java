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

import jd.toll.server.pages.examples.ExamplePage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.util.string.StringValue;


//@MountPath("/layers/allstores")
public class AllNodesPage extends ExamplePage
{
	private static final long serialVersionUID = 1L;
//    private FeedbackPanel fbp;


    public void formPage(final SortableActionsProvider dp){
//        fbp = new FeedbackPanel("feedback");
//        add(fbp);
//        fbp.setOutputMarkupId(true);
        if (!getPageParameters().isEmpty()){
            StringValue str = getPageParameters().get("del");
            if (!str.isEmpty() && !str.isNull()){
                info("Модуль удален: " + str.toString());
            }
        }
        add(new BookmarkablePageLink("add_store", AddNewNode.class));
        NodePanel sp = new NodePanel("data_table",dp);
        add(sp);
        sp.setOutputMarkupId(true);
        add(new PagingNavigator("navigator", sp.getDataView()));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        formPage(new SortableActionsProvider(new FilterModel()));
    }
}
