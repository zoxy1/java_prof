package jd.toll.server.domain;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.CascadeType.*;

/**
 * User: saturn
 * Date: 17.09.2014
 */
@Entity
@Table(name = "nodes")
public class XBeeNode {

    @Id
    @Column(name = "zig_bee_id_64")
    public String zigBeeId;

    @Column(name = "node_name")
    public String nodeName;

    @Column(name = "is_local")
    public boolean local = false;

    @Column(name = "create_time_stamp")
    public long createTimeStamp;

    @Column(name = "update_time_stamp")
    public long updateTimeStamp;


    @Column(name = "lat")
    public Double lat;

    @Column(name = "lon")
    public Double lon;

    @Column(name = "is_tracked")
    public boolean isTracked = true;

    private static final Logger log = LoggerFactory.getLogger(XBeeNode.class);

    public XBeeNode() {
    }

    public XBeeNode(
            String nodeId,
            long createTimeStamp,
            long updateTimeStamp,
            String nodeName,
            boolean isLocal
    ) {
        this.zigBeeId = nodeId;
        this.createTimeStamp = createTimeStamp;
        this.updateTimeStamp = updateTimeStamp;
        this.nodeName = nodeName;
        this.local = isLocal;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isTracked() {
        return isTracked;
    }

    public void setTracked(boolean isTracked) {
        this.isTracked = isTracked;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public long getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public void setUpdateTimeStamp(long updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public long getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(long createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public String getZigBeeId() {
        return zigBeeId;
    }

    public void setZigBeeId(String zigBeeId) {
        this.zigBeeId = zigBeeId;
    }

    public String getDH() {
        return this.zigBeeId.substring(0, 8);
    }

    public String getDL() {
        return this.zigBeeId.substring(8, zigBeeId.length());
    }

    public int[] getZigBeeIdAsInt() {
        if (StringUtils.isEmpty(zigBeeId)) {
            return null;
        }
        int[] result = new int[8];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.valueOf(zigBeeId.substring(i*2, i*2 + 2), 16);

        }
        return result;
    }
}
