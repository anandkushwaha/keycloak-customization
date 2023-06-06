package org.example.rest;

import org.example.spi.provider.CustomProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

public class CustomRestProvider implements RealmResourceProvider {

    private KeycloakSession keycloakSession;

    public CustomRestProvider(KeycloakSession keycloakSession) {
        this.keycloakSession = keycloakSession;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {

    }

    @GET
    @Produces("text/plain; charset=utf-8")
    public String get(){
        //String name = keycloakSession.getContext().getRealm().getName();
        String name = String.valueOf(keycloakSession.getContext().getRealm().getClientsCount());
        //String msg = keycloakSession.getProvider(CustomProvider.class).sayHello(name);
        return name;
    }

}
