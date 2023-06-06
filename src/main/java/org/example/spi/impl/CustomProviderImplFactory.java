package org.example.spi.impl;

import org.example.spi.provider.CustomProvider;
import org.example.spi.provider.CustomProviderFactory;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class CustomProviderImplFactory implements CustomProviderFactory {
    private static Logger logger = Logger.getLogger(CustomProviderFactory.class);
    private static String ID = "custom-provider-impl";
    @Override
    public CustomProvider create(KeycloakSession keycloakSession) {
        return new CustomProviderImpl();
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
