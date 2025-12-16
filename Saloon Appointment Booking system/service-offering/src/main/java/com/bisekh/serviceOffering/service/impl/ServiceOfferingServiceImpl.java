package com.bisekh.serviceOffering.service.impl;

import com.bisekh.serviceOffering.model.ServiceOffering;
import com.bisekh.serviceOffering.payload.dto.CategoryDto;
import com.bisekh.serviceOffering.payload.dto.SalonDTO;
import com.bisekh.serviceOffering.payload.dto.ServiceDto;
import com.bisekh.serviceOffering.repository.ServiceOfferingRepository;
import com.bisekh.serviceOffering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {


    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createService(SalonDTO salonDto, ServiceDto serviceDto, CategoryDto categoryDto) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDto.getImage());
        serviceOffering.setSalonId(salonDto.getId());
        serviceOffering.setName(serviceDto.getName());
        serviceOffering.setDescription(serviceDto.getDescription());
        serviceOffering.setCategory(categoryDto.getId());
        serviceOffering.setPrice(serviceDto.getPrice());
        serviceOffering.setDuration(serviceDto.getDuration());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering service) throws  Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(serviceId).orElse(null);
        if(serviceOffering == null){
            throw  new Exception("Service Does not exists with the given id: "+ serviceId);
        }

        serviceOffering.setImage(service.getImage());
        serviceOffering.setName(service.getName());
        serviceOffering.setDescription(service.getDescription());
        serviceOffering.setPrice(service.getPrice());
        serviceOffering.setDuration(service.getDuration());



        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalon(Long salonId, Long categoryId) {
        Set<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);

        if(categoryId != null){
            services = services.stream().filter((service) -> service.getCategory() !=null &&
                    service.getCategory().equals(categoryId)).collect(Collectors.toSet()
                    );
        }

        return services;
    }

    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {
        List<ServiceOffering> serviceOffering = serviceOfferingRepository.findAllById(ids);

        return new HashSet<>(serviceOffering);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws  Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElse(null);
        if(serviceOffering == null){
            throw  new Exception("Service Does not exists with the given id: "+ id);
        }
        return serviceOffering;
    }


}
