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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;
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
public class FormPanel extends Panel
{


    private final FilterModel backup;

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
		public InputForm(String name, final FilterModel filterModel)
		{
			super(name, new CompoundPropertyModel<FilterModel>(filterModel != null ? filterModel : new FilterModel()));

            add(new TextField<String>("store_name", String.class).setRequired(true).add(
                    new StringValidator(1, 200)));

            add(new TextField<String>("store_address", String.class).setRequired(false).add(
                    new StringValidator(1, 200)));

            FormComponent<Double> store_lat = new TextField<Double>("store_lat", Double.class).setRequired(false).add(
                    new RangeValidator<Double>(-90., +90.0));
            store_lat.setOutputMarkupId(true);
            add(store_lat);

            FormComponent<Double> store_lon = new TextField<Double>("store_lon", Double.class).setRequired(false).add(
                    new RangeValidator<Double>(-180., +180.0));
            store_lon.setOutputMarkupId(true);
            add(store_lon);


            add(new Button("createButton"));

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

            final FilterModel fm = (FilterModel) getDefaultModelObject();
            WicketApplication app = (WicketApplication) Application.get();
            if (app.getNodeService().getNode(fm.getStore_name()) != null) {
                error("Модуль '" + fm.getStore_name() + "' " + "уже существует, измените имя или удалите из БД");
                return;
            };

			XBeeNode node = new XBeeNode(fm.getStore_name(),System.currentTimeMillis(), System.currentTimeMillis(), fm.getStore_address(), false);
			node.setLat(fm.getStore_lat());
			node.setLon(fm.getStore_lon());
			try {
				app.getNodeService().save(node);
				info("Модуль : " + node.getZigBeeId() + " успешно сохранён");
				PageParameters pg = new PageParameters();
				pg.add("name", node.getZigBeeId());
				pg.add("new", true);
				setResponsePage(ViewNode.class, pg);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("saving error : " + e.getMessage());
				error("Ошибка сохранения нового модуля : " + node.getZigBeeId());
			}


            /*Coord to = new Coord(55.846291, 37.566056);
            Coord from = null;

            if (fm.getStore_lon() != null) {
                backup.setStore_lon(fm.getStore_lon());
            } else {
                fm.setStore_lon(backup.getStore_lon());
            }

            if (fm.getStore_lat() != null) {
                backup.setStore_lat(fm.getStore_lat());
            } else {
                fm.setStore_lat(backup.getStore_lat());
            }

            try {
                from = new Coord(backup.getStore_lat(), backup.getStore_lon());
            } catch (Exception e) {
                e.printStackTrace();
                error("Указанные координаты недостижимы по автодороге, переместите маркер склада ближе к существующей автодороге");
                return;
            }


            try {
                String route = app.getRouteService().getRoute(to, from, "car");
                //todo add new node to table
                Store nStore = app.getStoreService().addNew(fm);
                if (nStore != null) {
                    info("Склад " + nStore.getName() + " успешно добавлен");
                    setResponsePage(ViewNode.class, new PageParameters(){
                        {
                            add("name", fm.getStore_name());
                        }
                    });
                } else {
                    error("Ошибка при добавлении склада : " + fm);
                }

                return;
            } catch (IOException e) {
                e.printStackTrace();
                log.error("not found car route for new store : {}", from);
                error("Указанные координаты недостижимы по автодороге, переместите маркер склада ближе к существующей автодороге");
                return;
            }*/

			/*// Form validation successful. Display message showing edited model.
            try {
                switch (getAction()){
                    case "export":
//                        export();

                        break;
                    default:
                        if (check()) {
                            info("Фильтр сохранён: " + getDefaultModelObject());
                            setResponsePage(new AllNodesPage((FilterModel) getDefaultModelObject()));
                        } else {
                            //setResponsePage(getPage());
                        }
                }
            } finally {
                setAction("default");
            }*/

        }

        private boolean check() {
            /*FilterModel filterModel = (FilterModel) getDefaultModelObject();
            if (filterModel.getDaysBack() == null && !filterModel.getAllTimes()) {
                error("Количество дней не должно быть пустым");
                return false;
            } else {
                return true;
            }*/

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

/*        private void export(){
            File file = new File("/opt/exports/export-" + System.currentTimeMillis() / 1000 + ".csv");
            try {
                GateDatabaseLocator.getDatabase().exportToFile(file, (FilterModel) InputForm.this.getDefaultModelObject());
            } catch (IOException e) {
                error("Ошибка ввода вывода : " + e.getMessage());
                setResponsePage(getPage());
                return;
            }
            if (file.exists()) {
                log.info("File : " + file.getAbsolutePath() + " exists");
                IResourceStream resourceStream = new FileResourceStream(new org.apache.wicket.util.file.File(file));
                //info("Файл экспорта сохранён: " + file.getName());
                getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(resourceStream, file.getName()));
            } else {
                log.warn("File : " + file.getAbsolutePath() + " doesn't exists");
                info("Файл : " + file.getAbsolutePath() + " не существует");
                setResponsePage(getPage());
            }
        };*/
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
			setModel(new PropertyModel<Locale>(FormPanel.this, "locale"));
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
	public FormPanel(String name, FilterModel filterModel, FilterModel backup)
	{
        super(name);
        this.backup = backup;
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
