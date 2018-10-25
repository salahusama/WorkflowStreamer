package com.workflowstreamer.resources;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HelloWorldResourceTest {

    private static final String NAME = "Salah";
    private final HelloWorldResource helloWorldResource = new HelloWorldResource();

    @Test
    public void itSaysHelloWithName() throws Exception {
        assertThat(helloWorldResource.sayHello(Optional.of(NAME))).isEqualTo(String.format("Hello, %s", NAME));
    }

    @Test
    public void itSaysHelloToStranger() throws Exception {
        assertThat(helloWorldResource.sayHello(Optional.empty())).isEqualTo("Hello, Stranger");
    }
}