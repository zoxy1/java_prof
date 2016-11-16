package jd.toll.server.pages;

import com.google.common.base.Preconditions;
import jd.toll.server.config.ServerContext;
import jd.toll.server.pages.examples.SortingPage;
import jd.toll.server.pages.nodes.AllNodesPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;

/**
 * Created by saturn on 18.10.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServerContext.class,
        loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class PagesTest extends AbstractJUnit4SpringContextTests {

    private static WicketTester tester;

    @Before
    public void init() {
        getTester();
    }

    WicketTester getTester() {

        if (tester == null) {
            WebApplication application = (WebApplication) applicationContext.getBean("wicketApplication");
            Preconditions.checkNotNull(application);
            tester = new WicketTester(application);
        }
        return tester;
    }

    @Test
    public void homePageTest() throws Exception {
        tester.startPage(Home.class);
        tester.assertRenderedPage(Home.class);
    }

    @Test
    public void homeExtenderTest() throws Exception {
        tester.startPage(HomeExtender.class);
        tester.assertRenderedPage(HomeExtender.class);
    }

    @Test
    public void sortinPageTest() throws Exception {
        tester.startPage(SortingPage.class);
        tester.assertRenderedPage(SortingPage.class);
    }

    @Test
    public void allNodesPageTest() throws Exception {
        tester.startPage(AllNodesPage.class);
        tester.assertRenderedPage(AllNodesPage.class);
    }

}