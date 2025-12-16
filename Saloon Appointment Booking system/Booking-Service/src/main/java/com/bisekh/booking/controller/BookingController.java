package com.bisekh.booking.controller;


import com.bisekh.booking.domain.BookingStatus;
import com.bisekh.booking.dto.*;
import com.bisekh.booking.mapper.BookingMapper;
import com.bisekh.booking.model.Booking;
import com.bisekh.booking.model.SalonReport;
import com.bisekh.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking>  createBooking(
            @RequestParam Long salonId,
            @RequestBody BookingRequest bookingRequest

            ) throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);

        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.of(9, 0)); // 9 AM
        salon.setCloseTime(LocalTime.of(21, 0)); // 9 PM

        Set<ServiceDto> serviceDtoSet = new HashSet<>();

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(1L);

        serviceDto.setPrice(399);
        serviceDto.setDuration(45);
        serviceDto.setName("Hair Cut for men");
        serviceDtoSet.add(serviceDto);

        Booking booking = bookingService.createBooking(
                bookingRequest,
                user,
                salon,
                serviceDtoSet);
        return ResponseEntity.ok(booking);

    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(){
        List<Booking> bookings = bookingService.getBookingsByCustomer(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }


    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream()
                .map(booking -> {
                    return BookingMapper.toDTO(booking);
                }).collect(Collectors.toSet());
    }


    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(){
        List<Booking> bookings = bookingService.getBookingsBySalon(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingsById(
            @PathVariable Long bookingId
    ) throws  Exception{
        Booking booking = bookingService.getBookingsById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }




    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
            ) throws  Exception{
        Booking booking = bookingService.updateBooking(bookingId,status);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }


    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(
            @PathVariable Long salonId,
            @PathVariable(required = false) LocalDate date
            ) throws  Exception{
        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);
        List<BookingSlotDTO> slotDTOS = bookings.stream()
                .map(booking -> {
                    BookingSlotDTO slotDTO = new BookingSlotDTO();
                    slotDTO.setStartTime(booking.getStartTime());
                    slotDTO.setEndTime(booking.getEndTime());
                    return slotDTO;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(slotDTOS);
    }


    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(

    ) throws  Exception{
       SalonReport report = bookingService.getSalonReport(1L);
        return ResponseEntity.ok(report);
    }

    
}
