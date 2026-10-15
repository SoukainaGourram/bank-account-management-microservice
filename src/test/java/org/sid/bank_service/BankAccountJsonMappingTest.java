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
        HttpResponse<String> response = createAccount(
                "{\"balance\":8000,\"type\":\"SAVING_ACCOUNT\",\"currency\":\"EUR\"}");

        org.junit.jupiter.api.Assertions.assertEquals(200, response.statusCode(), response.body());
        org.junit.jupiter.api.Assertions.assertTrue(response.body().contains("\"balance\":8000.0"));
        org.junit.jupiter.api.Assertions.assertFalse(response.body().contains("\"solde\""));
    }

    @Test
    void shouldAcceptLegacySoldeAsBalanceAlias() throws Exception {
        HttpResponse<String> response = createAccount(
                "{\"solde\":1250,\"type\":\"CURRENT_ACCOUNT\",\"currency\":\"USD\"}");

        org.junit.jupiter.api.Assertions.assertEquals(200, response.statusCode(), response.body());
        org.junit.jupiter.api.Assertions.assertTrue(response.body().contains("\"balance\":1250.0"));
        org.junit.jupiter.api.Assertions.assertFalse(response.body().contains("\"solde\""));
    }

    @Test
    void shouldFindAccountsByType() throws Exception {
        createAccount("{\"balance\":8000,\"type\":\"SAVINGS_ACCOUNT\",\"currency\":\"EUR\"}");

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/bankAccounts/search/findByType?type=SAVINGS_ACCOUNT"))
                .header("Content-Type", APPLICATION_JSON.toString())
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        org.junit.jupiter.api.Assertions.assertEquals(200, response.statusCode(), response.body());
        org.junit.jupiter.api.Assertions.assertTrue(response.body().contains("\"type\":\"SAVINGS_ACCOUNT\""));
    }

    @Test
    void shouldExposeRepositorySearchByTypeAtCustomPath() throws Exception {
        createAccount("{\"balance\":8000,\"type\":\"SAVINGS_ACCOUNT\",\"currency\":\"EUR\"}");

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/bankAccounts/search/byType?type=SAVINGS_ACCOUNT"))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        org.junit.jupiter.api.Assertions.assertEquals(200, response.statusCode(), response.body());
        org.junit.jupiter.api.Assertions.assertTrue(response.body().contains("SAVINGS_ACCOUNT"), response.body());
    }

    private HttpResponse<String> createAccount(String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/bankAccounts"))
                .header("Content-Type", APPLICATION_JSON.toString())
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
}
