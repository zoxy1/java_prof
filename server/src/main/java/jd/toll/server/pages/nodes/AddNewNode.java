package jd.toll.server.pages.nodes;

//import com.google.gson.JsonObject;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.TextField;

import jd.toll.server.pages.examples.ExamplePage;

/*import org.wicketstuff.openlayers3.DefaultOpenLayersMap;
import org.wicketstuff.openlayers3.OpenLayersMap;
import org.wicketstuff.openlayers3.api.Feature;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.PersistentFeature;
import org.wicketstuff.openlayers3.api.View;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.geometry.Point;
import org.wicketstuff.openlayers3.api.interaction.Interaction;
import org.wicketstuff.openlayers3.api.interaction.Modify;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Tile;
import org.wicketstuff.openlayers3.api.layer.Vector;
import org.wicketstuff.openlayers3.api.source.Osm;
import org.wicketstuff.openlayers3.api.source.VectorSource;
import org.wicketstuff.openlayers3.api.style.Icon;
import org.wicketstuff.openlayers3.api.style.Style;
import org.wicketstuff.openlayers3.api.util.CorsPolicy;
import org.wicketstuff.openlayers3.behavior.FeatureChangeListener;*/


/**
 * Created by saturn on 04.10.2015.
 */
public class AddNewNode extends ExamplePage {

    private static final long serialVersionUID = 1L;
//    private OpenLayersMap map;
//    private PersistentFeature featureFrom;
//    private  LongLat fromLongLat = new LongLat(38.61728824326119, 57.7550499773117, "EPSG:4326").transform(View.DEFAULT_PROJECTION);
    private FilterModel fModel;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new FormPanel("form_panel", null, fModel = new FilterModel()));
//        add(new FeedbackPanel("feedback"));



//        Style styleFrom = new Style(new Icon("/img/marker-icon-green.png")
//                .crossOrigin(CorsPolicy.ANONYMOUS));



        // featureFrom for the site
        /*featureFrom = new PersistentFeature(
                new Point(fromLongLat.transform(View.DEFAULT_PROJECTION)),
                "NewStore");
        featureFrom.setStyle(styleFrom);

        Vector vectorFeaturesFrom = new Vector(new VectorSource(Arrays.<Feature>asList(featureFrom)));*/

        /*List<Layer> layers = Arrays.<Layer>asList(

                // a new tile layer with the map of the world
                new Tile("Streets",

                        // a new web map service tile layer
                        new Osm()),

                vectorFeaturesFrom
                        *//*,

                // vector of features
                vectorFeaturesTerminals,
                vectorFeaturesFrom,
                vectorFeaturesTo*//*
        );

        add(map = new DefaultOpenLayersMap("map",

                // create the model for our map
                Model.of(new Map(

                        // list of layers
                        layers,

//                        terminalOverlays,

                        // view for this map
                        new View(

                                // coordinate of Miles' office
                                fromLongLat.transform(View.DEFAULT_PROJECTION),

                                // zoom level for the view
                                7)).interactions(Arrays.<Interaction>asList(new Modify(featureFrom))))));

        map.add(new FeatureChangeListener(featureFrom) {

            private String selectFrom;

            @Override
            public void handleChange(AjaxRequestTarget target, String featureId, LongLat longLat, JsonObject properties) {


                Object dmo = getPage().getDefaultModelObject();
                System.err.println(dmo);

                selectFrom = ((long) ((double) longLat.getLongitude() * 10e6)) / 10e6 + ";" + ((long) ((double) longLat.getLatitude() * 10e6)) / 10e6;

                TextField<Double> cmp = (TextField<Double>) getPage().get("form_panel:inputForm:store_lat");

                fModel.setStore_lat((Double)longLat.getLatitude());

                cmp.setDefaultModelObject((Double)longLat.getLatitude());
                System.err.println(cmp);
                target.add(cmp);

                TextField<Double> cmp2 = (TextField<Double>) getPage().get("form_panel:inputForm:store_lon");
                fModel.setStore_lon((Double)longLat.getLongitude());
                cmp2.setDefaultModelObject((Double)longLat.getLongitude());
                System.out.println(cmp2);
                target.add(cmp2);
                //checkMove(target);
                //  fromLocationModel.setObject(longLat);
                // target.add(featureLocationLabel);
            }
        });*/
    }

}
