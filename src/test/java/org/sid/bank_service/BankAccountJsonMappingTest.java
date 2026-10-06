package org.sid.bank_service;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankAccountJsonMappingTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    void shouldAcceptSAVING_ACCOUNTPayload() throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/bankAccounts"))
                .header("Content-Type", APPLICATION_JSON.toString())
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"balance\":8000,\"type\":\"SAVING_ACCOUNT\",\"currency\":\"EUR\"}"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        org.junit.jupiter.api.Assertions.assertEquals(200, response.statusCode(), response.body());
    }
}
