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

import jd.toll.server.dao.XBeeNodeRepository;
import jd.toll.server.domain.XBeeNode;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * detachable model for an instance of store
 */
public class DetachableActivityModel extends LoadableDetachableModel<XBeeNode>
{

    @Autowired
    @SpringBean
	XBeeNodeRepository xBeeNodeRepository;

    private final String id;

	/**
	 * @param c
	 */
	public DetachableActivityModel(XBeeNode c)
	{
		this(c.getZigBeeId());
	}

	/**
	 * @param id
	 */
	public DetachableActivityModel(String id)
	{
		if (id == null)
		{
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	/**
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return id.hashCode();
	}

	/**
	 * used for dataview with ReuseIfModelsEqualStrategy item reuse strategy
	 *
	 * @see org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		else if (obj == null)
		{
			return false;
		}
		else if (obj instanceof DetachableActivityModel)
		{
			DetachableActivityModel other = (DetachableActivityModel)obj;
			return other.id.equals(id);
		}
		return false;
	}

	/**
	 * @see LoadableDetachableModel#load()
	 */
	@Override
	protected XBeeNode load()
	{
		return SortableActionsProvider.getDatabase().getNode(id);
	}
}
