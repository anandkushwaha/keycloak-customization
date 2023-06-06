package org.example.events;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.jboss.logging.Logger;


public class CustomEventListenerFactory implements EventListenerProviderFactory{
    private static String PROVIDER_ID="custom-event-listener";
    private static final Logger logger = Logger.getLogger(CustomEventListenerFactory.class);

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new CustomEventListener();
    }

    @Override
    public void init(Config.Scope scope) {
        logger.infof("%s Initialized...",this.getClass().getName());
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        keycloakSessionFactory
                .getSpis()
                .forEach(value ->
                        logger.debug(value.getProviderFactoryClass().getName()));
    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
