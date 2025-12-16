package com.bisekh.serviceOffering.controller;

import com.bisekh.serviceOffering.model.ServiceOffering;
import com.bisekh.serviceOffering.payload.dto.CategoryDto;
import com.bisekh.serviceOffering.payload.dto.SalonDTO;
import com.bisekh.serviceOffering.payload.dto.ServiceDto;
import com.bisekh.serviceOffering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDto serviceDto


    ){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(serviceDto.getCategory());
        ServiceOffering serviceOfferings = serviceOfferingService.createService(salonDTO,serviceDto,categoryDto);
        return  ResponseEntity.ok(serviceOfferings);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable Long serviceId,
            @RequestParam ServiceOffering serviceOffering

    ) throws  Exception{

        ServiceOffering serviceOfferings = serviceOfferingService.updateService(serviceId,serviceOffering);
        return  ResponseEntity.ok(serviceOfferings);
    }



}
