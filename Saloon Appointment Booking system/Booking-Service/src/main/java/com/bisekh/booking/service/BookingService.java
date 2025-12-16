package com.bisekh.booking.service;

import com.bisekh.booking.domain.BookingStatus;
import com.bisekh.booking.dto.BookingRequest;
import com.bisekh.booking.dto.SalonDTO;
import com.bisekh.booking.dto.ServiceDto;
import com.bisekh.booking.dto.UserDTO;
import com.bisekh.booking.model.Booking;
import com.bisekh.booking.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking,
                          UserDTO user,
                          SalonDTO salon,
                          Set<ServiceDto> serviceDtoSet) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBySalon(Long salonId);
    Booking getBookingsById(Long id) throws  Exception;
    Booking updateBooking(Long bookingId, BookingStatus status)  throws  Exception ;
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    void deleteBooking(Long bookingId);
    SalonReport getSalonReport(Long salonId);

}
