package fh.se.car.rental.fh.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import fh.se.car.rental.fh.currency.ws.client.CurrencyClient;
import fh.se.car.rental.fh.currency.ws.client.helper.ConvertResponse;
import fh.se.car.rental.fh.exceptions.CarLabelAlreadyInUse;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.CarRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {
  @Mock
  private CarRepository carService;

  @Mock
  private CurrencyClient currencyClient;

  @InjectMocks
  private CarController carController;

  @Test
  void testList_whenCarsExist_returnsCarsWithSpecifiedStateAndConvertedCurrency() {
    //given
    List<Car> carList = new ArrayList<>();
    carList.add(
      new Car(3L, "Golf8", "VM", 555D, "W-6575674", CarState.FREE, CurrencyCode.USD)
    );
    carList.add(
      new Car(4L, "Golf9", "VM", 557D, "W-6575611", CarState.INUSE, CurrencyCode.EUR)
    );
    given(carService.findAll()).willReturn(carList);
    given(currencyClient.convertCurrency(any(), any())).willReturn(new ConvertResponse());
    //when
    List<Car> testList = carController.list(CarState.FREE, CurrencyCode.AUD);
    //then
    assertEquals(1, testList.size());
    assertEquals(CurrencyCode.AUD, testList.get(0).getCurrency());
  }

  @Test
  void testAdd_whenCarDoesntExist_createsCar() {
    //given
    Car newCar = new Car(
      3L,
      "Golf8",
      "VM",
      555D,
      "W-6575674",
      CarState.FREE,
      CurrencyCode.USD
    );
    //given(carService.findById(3L)).willReturn(null);
    //when
    //carController.add(newCar);
    //then
    //verify(carService).save(newCar);
  }

  @Test
  void testAdd_whenCarExists_throwsException() {
    //given
    //Car newCar = new Car(3L, "Golf8","VM", 555D,"W-6575674", CarState.FREE, CurrencyCode.USD);
    //given(carService.findById(3L)).willReturn(java.util.Optional.of(newCar));
    //when
    //CarLabelAlreadyInUse myCaughtException = assertThrows(CarLabelAlreadyInUse.class, () -> carController.add(newCar));
    //then
    //assertEquals("Golf8 already in use!", myCaughtException.getMessage());
  }
}
