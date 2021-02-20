package fh.se.car.rental.fh.service;

import fh.se.car.rental.fh.model.Booking;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.repository.BookingRepository;
import fh.se.car.rental.fh.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> listAll(){
        return bookingRepository.findAll();
    }

    public void save(Booking booking){
        bookingRepository.save(booking);
    }

    public Booking get(Long id){
        return bookingRepository.findById(id).get();
    }

    public void delete(Long id){
        bookingRepository.deleteById(id);
    }


}
