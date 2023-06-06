package org.example.storage;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import java.util.HashMap;
import java.util.Map;

public class CustomUserStorageProviderFactory implements UserStorageProviderFactory<CustomUserStorageProvider> {

    private static Logger log = Logger.getLogger(CustomUserStorageProviderFactory.class);
    private static String ID = "custom-user-storage";

    private Map<String,String> users;

    @Override
    public CustomUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
        users = new HashMap<>();
        users.put("admin","secret");
        users.put("user1","123");
        users.put("user2","123");
        return new CustomUserStorageProvider(keycloakSession,componentModel,users);
    }

    @Override
    public String getId() {
        return ID;
    }
}
