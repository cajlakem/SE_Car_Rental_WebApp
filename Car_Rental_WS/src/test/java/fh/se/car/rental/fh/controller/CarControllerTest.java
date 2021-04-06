package fh.se.car.rental.fh.controller;

import fh.se.car.rental.fh.exceptions.CarLabelAlreadyInUse;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarRepository carService;

    @InjectMocks
    private CarController carController;


    @Test
    void testList_whenCarsExist_returnsCars() {
        //given
        List<Car> carList = new ArrayList<>();
        carList.add(new Car(3L, "Golf8","VM", 555D,"W-6575674", CarState.FREE, CurrencyCode.USD));
        carList.add(new Car(4L, "Golf9","VM", 557D,"W-6575611", CarState.FREE, CurrencyCode.USD));
        given(carService.findAll()).willReturn(carList);
        //when
        List<Car> testList = carController.list(CarState.FREE, CurrencyCode.AMD);
        //then
        assertEquals(carList.size(), testList.size());
    }

    @Test
    void testListWithParameters_whenCarsExist_returnsCars() {
        //given
        List<Car> carList = new ArrayList<>();
        carList.add(new Car(3L, "Golf8","VM", 555D,"W-6575674", CarState.FREE, CurrencyCode.USD));
        carList.add(new Car(4L, "Golf9","VM", 557D,"W-6575611", CarState.INUSE, CurrencyCode.USD));
        given(carService.findAll()).willReturn(carList);
        //when
        List<Car> testList = carController.list(CarState.INUSE, CurrencyCode.AMD);
        //then
        assertEquals(1, testList.size());
        assertEquals(CurrencyCode.AMD, testList.get(0).getCurrency());
    }

    @Test
    void testAdd_whenCarDoesntExist_createsCar() {
        //given
        Car newCar = new Car(3L, "Golf8","VM", 555D,"W-6575674", CarState.FREE, CurrencyCode.USD);
        given(carService.findById(3L)).willReturn(null);
        //when
        carController.add(newCar);
        //then
        verify(carService).save(newCar);
    }

    @Test
    void testAdd_whenCarExists_throwsException() {
        //given
        Car newCar = new Car(3L, "Golf8","VM", 555D,"W-6575674", CarState.FREE, CurrencyCode.USD);
        given(carService.findById(3L)).willReturn(java.util.Optional.of(newCar));
        //when
        CarLabelAlreadyInUse myCaughtException = assertThrows(CarLabelAlreadyInUse.class, () -> carController.add(newCar));
        //then
        assertEquals("Golf8 already in use!", myCaughtException.getMessage());
    }
}
