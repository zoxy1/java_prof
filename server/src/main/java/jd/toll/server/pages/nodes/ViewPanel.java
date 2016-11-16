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
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


/**
 * Example for form input.
 * 
 * @author Eelco Hillenius
 * @author Jonathan Locke
 */
public class ViewPanel extends Panel
{
	/**
	 * Form for collecting input.
	 */
	private class InputForm extends Form<FilterModel>
	{
        private final Logger log = LoggerFactory.getLogger(InputForm.class);
        private String action;

        /**
		 * Construct.
		 *
         * @param name
         *            Component name
         * @param filterModel
         */
		@SuppressWarnings("serial")
		public InputForm(String name, FilterModel filterModel)
		{
			super(name, new CompoundPropertyModel<FilterModel>(filterModel != null ? filterModel : new FilterModel()));

            add(new TextField<String>("store_name", String.class));

            add(new TextField<String>("store_address", String.class));

            add(new TextField<Double>("store_lat", Double.class));

            add(new TextField<Double>("store_lon", Double.class));

            add(new Button("updateButton"));

			add(new Button("cancelButton") {
                @Override
                public void onSubmit() {
                    setResponsePage(AllNodesPage.class);
                }
            }.setDefaultFormProcessing(false));
		}

		/**
		 * @see Form#onSubmit()
		 */
		@Override
		public void onSubmit()
		{

            FilterModel fm = (FilterModel) getDefaultModelObject();
            StringValue storeName;
            System.err.println(storeName = getPage().getPageParameters().get("name"));
            WicketApplication app = (WicketApplication) Application.get();

            XBeeNode store;
            if ((store =  app.getNodeService().getNode(storeName.toString())) == null) {
                error("Склад '" + fm.getStore_name() + "' " + "не существует, обновление данных невозможно");
                return;
            }

			store.setLat(fm.getStore_lat());
			store.setLon(fm.getStore_lon());
			store.setNodeName(fm.getStore_address());
			fm.setStore_address(store.getNodeName());
			fm.setStore_name(store.getZigBeeId());
            app.getNodeService().save(store);
            info("Сохранено успешно");
        }

        private boolean check() {
            return true;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getAction() {
            if (action == null) {
                return "null";
            }
            return action;
        }
    }

	/** list view to be nested in the form. */
	private static final class LinesListView extends ListView<String>
	{

		/**
		 * Construct.
		 *
		 * @param id
		 */
		public LinesListView(String id)
		{
			super(id);
			// always do this in forms!
			setReuseItems(true);
		}

		@Override
		protected void populateItem(ListItem<String> item)
		{
			// add a text field that works on each list item model (returns
			// objects of
			// type FormInputModel.Line) using property text.
			item.add(new TextField<String>("lineEdit", new PropertyModel<String>(
				item.getDefaultModel(), "text")));
		}
	}

	/**
	 * Choice for a locale.
	 */
	private final class LocaleChoiceRenderer extends ChoiceRenderer<Locale>
	{
		/**
		 * Constructor.
		 */
		public LocaleChoiceRenderer()
		{
		}

		/**
		 * @see IChoiceRenderer#getDisplayValue(Object)
		 */
		@Override
		public Object getDisplayValue(Locale locale)
		{
			return locale.getDisplayName(getLocale());
		}
	}

	/**
	 * Dropdown with Locales.
	 */
	/*private final class LocaleDropDownChoice extends DropDownChoice<Locale>
	{
		*//**
		 * Construct.
		 *
		 * @param id
		 *            component id
		 *//*
		public LocaleDropDownChoice(String id)
		{
			super(id, WicketApplication.LOCALES, new LocaleChoiceRenderer());

			// set the model that gets the current locale, and that is used for
			// updating the current locale to property 'locale' of FormInput
			setModel(new PropertyModel<Locale>(ViewPanel.this, "locale"));
		}

		*//**
		 * @see DropDownChoice#onSelectionChanged(Object)
		 *//*
		@Override
		public void onSelectionChanged(Locale newSelection)
		{
			// note that we don't have to do anything here, as our property
			// model allready calls FormInput.setLocale when the model is
			// updated

			// force re-render by setting the page to render to the bookmarkable
			// instance, so that the page will be rendered from scratch,
			// re-evaluating the input patterns etc
			setResponsePage(getPage());
		}

		*//**
		 * @see DropDownChoice#wantOnSelectionChangedNotifications()
		 *//*
		@Override
		protected boolean wantOnSelectionChangedNotifications()
		{
			// we want roundtrips when a the user selects another item
			return true;
		}
	}*/

	/** available sites for the multiple select. */
	private static final List<String> SITES = Arrays.asList("The Server Side", "Java Lobby",
		"Java.Net");

	/** available numbers for the radio selection. */
	static final List<String> NUMBERS = Arrays.asList("1", "2", "3");

    /**
	 * Constructor
	 */
	public ViewPanel(String name, FilterModel filterModel)
	{
        super(name);
		// Construct form and feedback panel and hook them up
		//final FeedbackPanel feedback = new FeedbackPanel("feedback");
		//add(feedback);
		add(new InputForm("inputForm", filterModel));
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

	private static class URLConverter implements IConverter<URL>
	{
		public static final URLConverter INSTANCE = new URLConverter();
		@Override
		public URL convertToObject(String value, Locale locale)
		{
			try
			{
				return new URL(value);
			}
			catch (MalformedURLException e)
			{
				throw new ConversionException("'" + value + "' is not a valid URL");
			}
		}

		@Override
		public String convertToString(URL value, Locale locale)
		{
			return value != null ? value.toString() : null;
		}
	}
}
