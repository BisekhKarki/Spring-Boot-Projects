package com.bisekh.service;

import com.bisekh.model.Salon;
import com.bisekh.payload.dto.SalonDTO;
import com.bisekh.payload.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalonService  {
    Salon createSalon(SalonDTO salon, UserDTO user);
    Salon updateSalon(SalonDTO salon, UserDTO user,Long salonId) throws  Exception;

    List<Salon> getAllSalons();
    Salon getSalonById(Long salonId) throws Exception;

    Salon getSalonByOwnerId(Long ownerId);
    List<Salon> searchSalonByCity(String city);

}
