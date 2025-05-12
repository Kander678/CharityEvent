package ser.mil.charityevent.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ser.mil.charityevent.controller.request.CharityEventRequest;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.infrastructure.repository.charity.CharityEventEntity;
import ser.mil.charityevent.infrastructure.repository.charity.CharityEventRepositorySQL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CharityEventControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private CharityEventRepositorySQL charityEventRepositorySQL;

    @Test
    void shouldAddCharityEvent() {
        //Given
        String name = "Charity Event";
        Currency currency = Currency.USD;
        CharityEventRequest charityEventRequest = new CharityEventRequest(name, currency);

        //When
        webTestClient.post()
                .uri("/charity-event/create")
                .bodyValue(charityEventRequest)
                .exchange()
                .expectStatus().isOk();

        //Then
        CharityEventEntity saved = charityEventRepositorySQL.getCharityEventByName(charityEventRequest.name());
        assertNotNull(saved);
        assertEquals(name, saved.getName());
        assertEquals(currency, saved.getCurrency());
    }

    @Test
    void shouldRejectNullCurrency() {
        //Given
        CharityEventRequest request = new CharityEventRequest("Charity with null currency", null);

        //When //Then
        webTestClient.post()
                .uri("/charity-event/create")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void shouldRejectDuplicateName() {
        //Given
        CharityEventRequest charityEventRequest = new CharityEventRequest("Duplicate", Currency.PLN);

        webTestClient.post()
                .uri("/charity-event/create")
                .bodyValue(charityEventRequest)
                .exchange()
                .expectStatus().isOk();

        //When //Then
        webTestClient.post()
                .uri("/charity-event/create")
                .bodyValue(charityEventRequest)
                .exchange()
                .expectStatus().isEqualTo(409);
    }

}