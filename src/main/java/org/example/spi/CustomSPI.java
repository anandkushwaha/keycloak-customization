package org.example.spi;

import org.example.spi.provider.CustomProvider;
import org.example.spi.provider.CustomProviderFactory;
import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class CustomSPI implements Spi {

    private static String NAME = "custom-spi";
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return CustomProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return CustomProviderFactory.class;
    }
}
