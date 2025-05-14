package ser.mil.charityevent.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ser.mil.charityevent.controller.request.CollectionBoxAddMoneyRequest;
import ser.mil.charityevent.controller.request.CollectionBoxPairRequest;
import ser.mil.charityevent.controller.request.CollectionBoxRequest;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.box.CollectionBoxRepository;
import ser.mil.charityevent.domain.box.model.CollectionBox;
import ser.mil.charityevent.domain.charity.CharityEventRepository;
import ser.mil.charityevent.domain.charity.CharityEventService;
import ser.mil.charityevent.domain.charity.model.Account;
import ser.mil.charityevent.domain.charity.model.CharityEvent;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CollectionBoxControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CollectionBoxRepository collectionBoxRepository;

    @Autowired
    private CharityEventRepository charityEventRepository;

    private final static String charityEventName = "TestEvent";
    @Autowired
    private CharityEventService charityEventService;

    @BeforeEach
    void setup() {
        charityEventRepository.save(
                new CharityEvent("1", charityEventName, new Account(BigDecimal.ZERO, Currency.USD)));
    }

    @Test
    void shouldCreateCollectionBox() {
        //Given
        CollectionBoxRequest request = new CollectionBoxRequest(Currency.USD);

        //When
        String id = webTestClient.post()
                .uri("/collectionBox/create")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(id);
        assertTrue(collectionBoxRepository.findById(id).isPresent());
    }

    @Test
    void shouldPairCollectionBoxWithEvent() {
        //Given
        String boxId = createCollectionBoxViaController(Currency.USD);
        CollectionBoxPairRequest pairRequest = new CollectionBoxPairRequest(boxId, charityEventName);

        //When //Then
        webTestClient.post()
                .uri("/collectionBox/pair")
                .bodyValue(pairRequest)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldAddMoneyToAssignedBox() {
        //Given
        String boxId = createCollectionBoxViaController(Currency.USD);

        CollectionBoxPairRequest pairRequest = new CollectionBoxPairRequest(boxId, charityEventName);
        webTestClient.post()
                .uri("/collectionBox/pair")
                .bodyValue(pairRequest)
                .exchange()
                .expectStatus().isOk();

        CollectionBoxAddMoneyRequest addMoneyRequest = new CollectionBoxAddMoneyRequest(Currency.USD, 75.50);

        //When //Then
        webTestClient.post()
                .uri("/collectionBox/add-money?collectionBoxId=" + boxId)
                .bodyValue(addMoneyRequest)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldRejectAddMoneyToUnassignedBox() {
        //Given
        String boxId = createCollectionBoxViaController(Currency.USD);

        CollectionBoxAddMoneyRequest addMoneyRequest = new CollectionBoxAddMoneyRequest(Currency.USD, 50.0);

        //When //Then
        webTestClient.post()
                .uri("/collectionBox/add-money?collectionBoxId=" + boxId)
                .bodyValue(addMoneyRequest)
                .exchange()
                .expectStatus().isEqualTo(409);
    }

    @Test
    void shouldConvertMoneyFromBoxToEventAccount() {
        // Given
        String boxId = createCollectionBoxViaController(Currency.USD);

        CollectionBoxPairRequest pairRequest = new CollectionBoxPairRequest(boxId, charityEventName);
        webTestClient.post()
                .uri("/collectionBox/pair")
                .bodyValue(pairRequest)
                .exchange()
                .expectStatus().isOk();

        CollectionBoxAddMoneyRequest addMoneyRequest = new CollectionBoxAddMoneyRequest(Currency.USD, 100.0);
        webTestClient.post()
                .uri("/collectionBox/add-money?collectionBoxId=" + boxId)
                .bodyValue(addMoneyRequest)
                .exchange()
                .expectStatus().isOk();

        //When
        webTestClient.post()
                .uri("/collectionBox/convert")
                .bodyValue(pairRequest)
                .exchange()
                .expectStatus().isOk();

        //Then
        BigDecimal balance = charityEventService.getCharityEventByName(charityEventName).getAccount().balance();
        assertEquals(0, balance.compareTo(BigDecimal.valueOf(100.0)));
    }

    @Test
    void shouldDeleteCollectionBox() {
        // Given
        String boxId = createCollectionBoxViaController(Currency.USD);

        //When
        webTestClient.delete()
                .uri("/collectionBox/delete?collectionBoxId=" + boxId)
                .exchange()
                .expectStatus().isOk();

        // Then
        boolean deleted = collectionBoxRepository.findById(boxId).map(CollectionBox::isDeleted).orElse(false);
        assertTrue(deleted);
    }


    private String createCollectionBoxViaController(Currency currency) {
        CollectionBoxRequest request = new CollectionBoxRequest(currency);
        return webTestClient.post()
                .uri("/collectionBox/create")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }
}