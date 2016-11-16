package jd.toll.server;

import jd.toll.server.dao.XBeeNodeRepository;
import jd.toll.server.pages.Home;
import jd.toll.server.pages.HomeExtender;
import jd.toll.server.pages.LoginPage;
import jd.toll.server.pages.SecureWebSession;
import jd.toll.server.pages.examples.ContactsDatabase;
import jd.toll.server.pages.examples.SortingPage;
import jd.toll.server.pages.nodes.AllNodesPage;
import jd.toll.server.pages.nodes.ViewNode;
import jd.toll.server.services.NodeService;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by saturn on 17.10.2016.
 */
public class WicketApplication extends AuthenticatedWebApplication implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    @SpringBean
    XBeeNodeRepository xBeeNodeRepository;

    @Autowired
    @SpringBean
    NodeService nodeService;

    private final ContactsDatabase contactsDB = new ContactsDatabase(50);

    @Override
    public Class<? extends Page> getHomePage() {
        return Home.class;
    }

    @Override
    protected void init() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        mountPage("/extender", HomeExtender.class);
        mountPage("/login", LoginPage.class);
        mountPage("/home", Home.class);
        mountPage("/sort", SortingPage.class);
        mountPage("/nodes", AllNodesPage.class);
        mountPage("/view", ViewNode.class);
        if (xBeeNodeRepository == null) throw new IllegalStateException("node repo is not initialized");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }
    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return SecureWebSession.class;
    }

    public NodeService getNodeService() {
        return nodeService;
    }

    public XBeeNodeRepository getNodeRepository() {
        return xBeeNodeRepository;
    }

    public ContactsDatabase getContactsDB() {
        return contactsDB;
    }
}
