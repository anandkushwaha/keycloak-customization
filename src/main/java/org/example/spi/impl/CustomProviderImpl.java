package org.example.spi.impl;

import org.example.spi.provider.CustomProvider;

public class CustomProviderImpl implements CustomProvider {
    @Override
    public String sayHello(String name) {
        return "Hello "+name;
    }

    @Override
    public void close() {

    }
}
