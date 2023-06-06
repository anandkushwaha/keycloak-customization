package org.example.events;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class CustomEventListener implements EventListenerProvider{

    public CustomEventListener(){}
    @Override
    public void onEvent(Event event) {
        System.err.printf("ID: %s, Time: %s, Realm: %s",event.getId(),event.getTime(),event.getRealmId());
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}
