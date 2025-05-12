package ser.mil.charityevent.domain.charity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.charity.model.CharityEvent;
import ser.mil.charityevent.domain.exception.DomainException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharityEventServiceTest {
    @Mock
    private CharityEventRepository charityEventRepository;

    @InjectMocks
    private CharityEventService charityEventService;

    @Test
    void shouldCreateCharityEvent_whenValidInput() {
        //Given
        String name = "Valid Name";
        Currency currency = Currency.PLN;

        when(charityEventRepository.existsByName(name)).thenReturn(false);

        //When
        CharityEvent result = charityEventService.addCharityEvent(name, currency);

        //Then
        assertNotNull(result);
        assertEquals(name, result.name());
        assertEquals(currency, result.account().currency());
        verify(charityEventRepository).save(any(CharityEvent.class));
    }

    @Test
    void shouldThrow_whenNameTooShort() {
        //Given
        String name = "ab";
        Currency currency = Currency.PLN;

        //When //Then
        DomainException ex = assertThrows(
                DomainException.class, () -> charityEventService.addCharityEvent(name, currency));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertTrue(
                ex.getMessage().contains("Invalid event name: must be 3-30 characters, no leading/trailing spaces."));
    }

    @Test
    void shouldThrow_whenNameHasLeadingWhitespace() {
        //Given
        String name = " abc";
        Currency currency = Currency.PLN;

        //When //Then
        DomainException ex = assertThrows(
                DomainException.class, () -> charityEventService.addCharityEvent(name, currency));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void shouldThrow_whenNameAlreadyExists() {
        //Given
        String name = "Valid Name";
        Currency currency = Currency.PLN;
        when(charityEventRepository.existsByName(name)).thenReturn(true);

        //When //Then
        DomainException ex = assertThrows(
                DomainException.class, () -> charityEventService.addCharityEvent(name, currency));
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertTrue(ex.getMessage().contains("Charity event name already exists."));
    }

    @Test
    void shouldThrow_whenNameWith30Characters() {
        //Given
        String name = "QWERTYUIOPASDFGHJKLZXCVBNM1234"; //30 chars
        Currency currency = Currency.PLN;

        when(charityEventRepository.existsByName(name)).thenReturn(false);

        //When
        CharityEvent result = charityEventService.addCharityEvent(name, currency);

        //Then
        assertEquals(name, result.name());
        verify(charityEventRepository).save(any(CharityEvent.class));
    }

    @Test
    void shouldThrow_whenNameIsOver30Characters() {
        String name = "QWERTYUIOPASDFGHJKLZXCVBNM12345"; // 31 chars
        Currency currency = Currency.USD;

        DomainException ex = assertThrows(DomainException.class,
                () -> charityEventService.addCharityEvent(name, currency));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }
}