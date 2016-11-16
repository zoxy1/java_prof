package jd.toll.server.pages.nodes;

import jd.toll.server.domain.XBeeNode;
import org.apache.wicket.util.io.IClusterable;


/**
 * Simple model object for FormInput example. Has a number of simple properties that can be
 * retrieved and set.
 */
public final class FilterModel implements IClusterable
{
    private String store_name;
    private String store_address;
    private Double store_lat;
    private Double store_lon;
    private Double store_income = 0.;
    private Double store_outcome = 0.;
    private Double store_balance = 0.;

    public FilterModel(XBeeNode node) {
        store_name = node.getZigBeeId();
        store_address = node.getNodeName();
        store_lat = node.getLat();
        store_lon = node.getLon();
    }


    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public Double getStore_lat() {
        return store_lat;
    }

    public void setStore_lat(Double store_lat) {
        this.store_lat = store_lat;
    }

    public Double getStore_lon() {
        return store_lon;
    }

    public void setStore_lon(Double store_lon) {
        this.store_lon = store_lon;
    }

    public Double getStore_income() {
        return store_income;
    }

    public Double getStore_outcome() {
        return store_outcome;
    }

    public Double getStore_balance() {
        return store_balance;
    }

    /**
     * Construct.
     */
    public FilterModel()
    {
    }



    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder b = new StringBuilder();
        b.append("модуль = ")
                .append(store_name)
                .append("\n, адрес = ")
                .append(store_address)
                .append("\n, координаты = ")
                .append("" + store_lat)
                .append(", " + store_lon + "\n");


        return b.toString();
    }
}