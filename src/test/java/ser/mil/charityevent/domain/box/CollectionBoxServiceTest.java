package ser.mil.charityevent.domain.box;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.box.model.CollectionBox;
import ser.mil.charityevent.domain.charity.CharityEventRepository;
import ser.mil.charityevent.domain.charity.CharityEventService;
import ser.mil.charityevent.domain.charity.model.Account;
import ser.mil.charityevent.domain.charity.model.CharityEvent;
import ser.mil.charityevent.domain.exception.DomainException;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionBoxServiceTest {

    @Mock
    private CollectionBoxRepository collectionBoxRepository;

    @Mock
    private CharityEventRepository charityEventRepository;

    @Mock
    private CharityEventService charityEventService;

    @InjectMocks
    private CollectionBoxService collectionBoxService;

    @Test
    void shouldAddCollectionBox_whenCurrencyValid() {
        //Given
        Currency currency = Currency.PLN;

        //When
        collectionBoxService.addCollectionBox(currency);
        //Then
        verify(collectionBoxRepository).save(any(CollectionBox.class));
    }

    @Test
    void shouldThrow_whenCurrencyIsNull() {
        //Given //When
        DomainException ex = assertThrows(DomainException.class,
                () -> collectionBoxService.addCollectionBox(null));

        //Then
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void shouldPairBoxWithEvent_whenValidInput() {
        //Given
        String boxId = "123";
        String eventName = "Charity";

        CollectionBox box = mock(CollectionBox.class);
        CharityEvent event = new CharityEvent("1", eventName, null);

        when(box.isAssigned()).thenReturn(false);
        when(box.isEmpty()).thenReturn(true);
        when(collectionBoxRepository.findById(boxId)).thenReturn(Optional.of(box));
        when(charityEventService.getCharityEventByName(eventName)).thenReturn(event);

        //When
        collectionBoxService.pairCollectionBoxWithCharityEvent(boxId, eventName);

        //Then
        verify(box).setCharityEvent(event);
        verify(box).setAssigned(true);
        verify(collectionBoxRepository).save(box);
    }

    @Test
    void shouldThrow_whenBoxNotEmpty() {
        //Given
        String boxId = "1";
        String eventName = "event";

        CollectionBox box = mock(CollectionBox.class);
        when(collectionBoxRepository.findById(boxId)).thenReturn(Optional.ofNullable(box));
        when(charityEventService.getCharityEventByName(eventName)).thenReturn(new CharityEvent("1", eventName, null));
        assert box != null;
        when(box.isAssigned()).thenReturn(false);
        when(box.isEmpty()).thenReturn(false);

        //When
        DomainException ex = assertThrows(DomainException.class,
                () -> collectionBoxService.pairCollectionBoxWithCharityEvent(boxId, eventName));

        //Then
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void shouldAddMoney_whenBoxValid() {
        //Given
        String boxId = "1";
        Currency currency = Currency.PLN;

        CollectionBox box = new CollectionBox(boxId, false, true, new HashMap<>());
        box.setCharityEvent(new CharityEvent("1", "Test", null));

        when(collectionBoxRepository.findById(boxId)).thenReturn(Optional.of(box));

        //When
        collectionBoxService.addMoneyToCollectionBox(currency, 100.0, boxId);

        //Then
        assertEquals(100.0, box.getCollectedMoney().get(currency));
        verify(collectionBoxRepository).save(box);
    }

    @Test
    void shouldThrow_whenAmountIsNegative() {
        //Given //When
        DomainException ex = assertThrows(DomainException.class,
                () -> collectionBoxService.addMoneyToCollectionBox(Currency.PLN, -1.0, "id"));

        //Then
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void shouldThrow_whenBoxNotAssignedToEvent() {
        //Given
        CollectionBox box = new CollectionBox("id", false, true, new HashMap<>());
        when(collectionBoxRepository.findById("id")).thenReturn(Optional.of(box));

        //Then
        DomainException ex = assertThrows(DomainException.class,
                () -> collectionBoxService.addMoneyToCollectionBox(Currency.PLN, 10.0, "id"));

        //Then
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void shouldTransferMoneyToEventAccount_whenValidInput() {
        // Given
        String boxId = "box1";
        String eventName = "CharityEvent";

        Map<Currency, Double> collected = new EnumMap<>(Currency.class);
        collected.put(Currency.PLN, 100.0);

        CollectionBox box = new CollectionBox(boxId, false, true, collected);
        Account account = new Account(BigDecimal.valueOf(50.00), Currency.PLN);
        CharityEvent event = new CharityEvent("1", eventName, account);

        when(collectionBoxRepository.findById(boxId)).thenReturn(Optional.of(box));
        when(charityEventService.getCharityEventByName(eventName)).thenReturn(event);

        // When
        collectionBoxService.transferMoneyFromCollectionBoxToEventAccount(boxId, eventName);

        // Then
        assertTrue(box.isEmpty());
        assertEquals(0.0, box.getCollectedMoney().get(Currency.PLN));
        assertEquals(BigDecimal.valueOf(150.00), event.getAccount().balance());

        verify(collectionBoxRepository).save(box);
        verify(charityEventRepository).save(event);
    }

    @Test
    void shouldMarkCollectionBoxAsDeleted_whenDeleteCalled() {
        // Given
        String boxId = "box1";
        CollectionBox box = new CollectionBox(boxId, true, false, new HashMap<>());

        when(collectionBoxRepository.findById(boxId)).thenReturn(Optional.of(box));

        // When
        collectionBoxService.deleteCollectionBox(boxId);

        // Then
        assertTrue(box.isDeleted());
        verify(collectionBoxRepository).save(box);
    }

    @Test
    void shouldThrow_whenCollectionBoxNotFound() {
        //Given
        when(collectionBoxRepository.findById("notExist")).thenReturn(Optional.empty());

        //When
        DomainException ex = assertThrows(DomainException.class,
                () -> collectionBoxService.transferMoneyFromCollectionBoxToEventAccount(
                        "notExist", "Event"));

        //Then
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void shouldThrow_whenBoxIsDeleted() {
        //Given
        CollectionBox box = new CollectionBox("id", true, false, new HashMap<>());
        box.setDeleted(true);
        when(collectionBoxRepository.findById("id")).thenReturn(Optional.of(box));

        //When
        DomainException ex = assertThrows(DomainException.class,
                () -> collectionBoxService.transferMoneyFromCollectionBoxToEventAccount(
                        "id", "Event"));

        //Then
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void shouldThrow_whenNoMoneyToTransfer() {
        //Given
        Map<Currency, Double> collected = new EnumMap<>(Currency.class);
        collected.put(Currency.PLN, 0.0);

        CollectionBox box = new CollectionBox("id", false, false, collected);
        Account account = new Account(BigDecimal.ZERO, Currency.PLN);
        CharityEvent event = new CharityEvent("1", "E", account);

        when(collectionBoxRepository.findById("id")).thenReturn(Optional.of(box));
        when(charityEventService.getCharityEventByName("E")).thenReturn(event);

        //When
        DomainException ex = assertThrows(DomainException.class,
                () -> collectionBoxService.transferMoneyFromCollectionBoxToEventAccount(
                        "id", "E"));

        //Then
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }


}