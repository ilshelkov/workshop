package com.teamcity.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.teamcity.api.models.BaseModel;
import lombok.SneakyThrows;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static com.github.tomakehurst.wiremock.common.ContentTypes.CONTENT_TYPE;

public final class WireMock {

    private static WireMockServer wireMockServer;

    @SneakyThrows
    public static void setupServer(MappingBuilder mappingBuilder, int status, BaseModel model) {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        wireMockServer.start();

        var jsonModel = new ObjectMapper().writeValueAsString(model);

        wireMockServer.stubFor(mappingBuilder
                .willReturn(aResponse()
                        .withStatus(status)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(jsonModel)));
    }

    public static void stopServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
        }
    }

}
