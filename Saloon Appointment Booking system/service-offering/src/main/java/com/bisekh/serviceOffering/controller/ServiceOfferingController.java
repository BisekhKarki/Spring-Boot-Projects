package com.bisekh.serviceOffering.controller;


import com.bisekh.serviceOffering.model.ServiceOffering;
import com.bisekh.serviceOffering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(@PathVariable Long salonId, @RequestParam(required=false) Long categoryId){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalon(salonId,categoryId);
        return  ResponseEntity.ok(serviceOfferings);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServicesById(@PathVariable Long id) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingService.getServiceById(id);
        return  ResponseEntity.ok(serviceOffering);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServicesByIds(@PathVariable Set<Long> ids) throws Exception {
        Set<ServiceOffering> serviceOffering = serviceOfferingService.getServiceByIds(ids);
        return  ResponseEntity.ok(serviceOffering);
    }











}
