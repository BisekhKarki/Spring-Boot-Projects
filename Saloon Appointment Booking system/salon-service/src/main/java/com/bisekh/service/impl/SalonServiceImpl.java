package com.bisekh.service.impl;

import com.bisekh.model.Salon;
import com.bisekh.payload.dto.SalonDTO;
import com.bisekh.payload.dto.UserDTO;
import com.bisekh.repository.SalonRepository;
import com.bisekh.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;


    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setPhoneNumber(req.getPhoneNumber());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        return  salonRepository.save(salon) ;
    }

    @Override
    public Salon updateSalon(SalonDTO req, UserDTO user, Long salonId) throws Exception {
        Salon existingSalon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon does not exist"));

        if (!req.getOwnerId().equals(user.getId())) {
            throw new Exception("You don't have permission to update this salon");
        }

        if (req.getName() != null) existingSalon.setName(req.getName());
        if (req.getAddress() != null) existingSalon.setAddress(req.getAddress());
        if (req.getEmail() != null) existingSalon.setEmail(req.getEmail());
        if (req.getCity() != null) existingSalon.setCity(req.getCity());
        if (req.getPhoneNumber() != null) existingSalon.setPhoneNumber(req.getPhoneNumber());
        if (req.getImages() != null) existingSalon.setImages(req.getImages());
        if (req.getOpenTime() != null) existingSalon.setOpenTime(req.getOpenTime());
        if (req.getCloseTime() != null) existingSalon.setCloseTime(req.getCloseTime());

        existingSalon.setOwnerId(user.getId());

        return salonRepository.save(existingSalon);
    }

    @Override
    public List<Salon> getAllSalons() {

        return  salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElse(null);
        if(salon == null){
            throw new Exception("Salon does not exists");
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {

        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }
}
