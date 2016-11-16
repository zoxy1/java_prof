package jd.toll.server.services;

import jd.toll.server.dao.XBeeNodeRepository;
import jd.toll.server.domain.XBeeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author Saturn
 */
@Service("nodeService")
public class NodeService {

    @Autowired
    XBeeNodeRepository xBeeNodeRepository;

    private final Map<String, XBeeNode> nodes = new HashMap<>();

    @PostConstruct
    public void validate() {
        initList();
    }

    private void initList() {
        synchronized (nodes) {
            nodes.clear();
            List<XBeeNode> list = (List) xBeeNodeRepository.findAll();
            for (XBeeNode xBeeNode : list) {
                nodes.put(xBeeNode.getZigBeeId(), xBeeNode);
            }
        }
    }

    public boolean hasNode(String nodeID) {
        synchronized (nodes) {
            return nodes.keySet().contains(nodeID);
        }
    }

    public XBeeNode getNode(String nodeID) {
        synchronized (nodes) {
            return nodes.get(nodeID);
        }
    }

    public Set<String> getNodeSet() {
        synchronized (nodes) {
            Set<String> set = new HashSet<>();
            set.addAll(nodes.keySet());
            return set;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(NodeService.class);

    public void save(XBeeNode xBeeNode) {
        synchronized (nodes) {
            if (xBeeNode.isLocal()) {
                List<XBeeNode> localList = (List) xBeeNodeRepository.findAll();
                for (XBeeNode node : localList) {
                    node.setLocal(false);
                }
                xBeeNodeRepository.save(localList);
                initList(localList);
            }
            xBeeNodeRepository.save(xBeeNode);
            nodes.put(xBeeNode.getZigBeeId(), xBeeNode);
        }
    }

    private void initList(List<XBeeNode> localList) {

        synchronized (nodes) {
            nodes.clear();
            List<XBeeNode> list = localList;
            for (XBeeNode xBeeNode : list) {
                nodes.put(xBeeNode.getZigBeeId(), xBeeNode);
            }
        }
    }

    public void delete(XBeeNode xBeeNode) {
        xBeeNodeRepository.delete(xBeeNode);
        initList();
    }

    public List<XBeeNode> getNodeList() {
        List<XBeeNode> all = (List<XBeeNode>) xBeeNodeRepository.findAll();
        initList(all);
        for (XBeeNode xBeeNode : all) {
            log.info("{} {} {}", xBeeNode.getZigBeeId(), xBeeNode.getLat(), xBeeNode.getLon());
        }
        return all;
    }

    public List<String> getNodeIDList() {
        Map<String, String> all = new HashMap<>();
        Map<Float, String> kipsToIds = new HashMap<>();
        List<XBeeNode> nodeList = getNodeList();
        float[] kips = new float[nodeList.size()];
        int i = 0;
        for (XBeeNode node : nodeList) {
            all.put(node.getNodeName(), node.getZigBeeId());
            try {
                kips[i] = Float.valueOf(node.getNodeName());
                kipsToIds.put(kips[i], node.getZigBeeId());
            } catch (Throwable e) {
                kips[i] = Float.MAX_VALUE;
                e.printStackTrace();
            }
            i++;
        }
        Arrays.sort(kips);
        ArrayList<String> result = new ArrayList<>();
        for (int j = 0; j < kips.length; j++) {
            float kip = kips[j];
            if (kipsToIds.get(kip) != null) result.add(kipsToIds.get(kip));
        }
        ArrayList<String> allList = new ArrayList<String>();
        allList.addAll(all.values());

        allList.removeAll(result);
        result.addAll(allList);

        return result;
    }

    @Scheduled(initialDelay = 3000, fixedDelay = 5000)
    public void sendCommand() {
        log.info("Send command !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}