package org.example.rest;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class CustomRestProviderFactory implements RealmResourceProviderFactory {

    private static Logger logger = Logger.getLogger(CustomRestProviderFactory.class);

    private static String ID = "simple";

    @Override
    public RealmResourceProvider create(KeycloakSession keycloakSession) {
        return new CustomRestProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {
        logger.infof("%s initialized...",this.getClass().getName());
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return ID;
    }
}
