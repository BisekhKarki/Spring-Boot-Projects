package com.bisekh.serviceOffering.service;

import com.bisekh.serviceOffering.model.ServiceOffering;
import com.bisekh.serviceOffering.payload.dto.CategoryDto;
import com.bisekh.serviceOffering.payload.dto.SalonDTO;
import com.bisekh.serviceOffering.payload.dto.ServiceDto;

import java.util.List;
import java.util.Set;

public interface ServiceOfferingService {


    ServiceOffering createService(SalonDTO salonDto,
                                  ServiceDto serviceDto,
                                  CategoryDto categoryDto);


    ServiceOffering updateService(Long serviceId,ServiceOffering service) throws  Exception;

    Set<ServiceOffering> getAllServiceBySalon(Long salonId, Long categoryId);

    Set<ServiceOffering> getServiceByIds(Set<Long> ids);


    ServiceOffering getServiceById(Long id) throws  Exception;

}
