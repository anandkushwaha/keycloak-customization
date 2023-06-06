package org.example.spi.provider;

import org.keycloak.provider.Provider;

public interface CustomProvider extends Provider {

    String sayHello(String name);

}
