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
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * implementation of IDataProvider for stores that keeps track of sort information
 */
public class SortableActionsProvider extends SortableDataProvider<XBeeNode, String> implements IFilterStateLocator<NodeFilter>
{

    private NodeFilter storeFilter = new NodeFilter();

	/**
	 * constructor
     * @param filterModel
     */
	public SortableActionsProvider(FilterModel filterModel)
	{
		// set default sort
		setSort("firstName", SortOrder.ASCENDING);
	}

	@Override
	public Iterator<XBeeNode> iterator(long first, long count)
	{
		List<XBeeNode> storesFound = getDatabase().getNodeList();

		return filterStores(storesFound).
			subList((int) first, (int) (first + count)).
			iterator();
	}

    public static NodeService getDatabase()
    {
        WicketApplication app = (WicketApplication) Application.get();
        return app.getNodeService();
    }

    private List<XBeeNode> filterStores(List<XBeeNode> storesFound) {
        ArrayList<XBeeNode> result = new ArrayList<>();
        for (XBeeNode store : storesFound) {
            result.add(store);
        }
        return result;
    }

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
	 */
	@Override
	public long size()
	{
		return getDatabase().getNodeList().size();
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#model(Object)
	 */
	@Override
	public IModel<XBeeNode> model(XBeeNode object)
	{
		return new DetachableActivityModel(object);
	}

	@Override
	public NodeFilter getFilterState()
	{
	    return storeFilter;
	}

	@Override
	public void setFilterState(NodeFilter state)
	{
	    storeFilter = state;
	}
}
