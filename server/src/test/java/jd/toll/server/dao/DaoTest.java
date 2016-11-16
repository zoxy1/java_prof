package jd.toll.server.dao;

import jd.toll.server.config.ServerContext;
import jd.toll.server.domain.XBeeNode;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by saturn on 18.10.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServerContext.class,
        loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class DaoTest {

    @Autowired
    XBeeNodeRepository xBeeNodeRepository;
    private String zigBeeId;

    @Test
    public void daoTest() throws Exception {
        Iterable<XBeeNode> list = xBeeNodeRepository.findAll();

        for (XBeeNode xBeeNode : list) {
            zigBeeId = xBeeNode.getZigBeeId();
            System.out.println(zigBeeId);
        }
    }

}