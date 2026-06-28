package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.LocationRequest;
import com.laneway.empportal.dto.response.LocationResponse;
import com.laneway.empportal.entity.Location;
import com.laneway.empportal.exception.DuplicateResourceException;
import com.laneway.empportal.exception.ResourceNotFoundException;
import com.laneway.empportal.repository.LocationRepository;
import com.laneway.empportal.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public LocationResponse create(LocationRequest request) {

        if (locationRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Location with this name already exists.");
        }

        Location location = Location.builder()
                .name(request.getName())
                .build();

        locationRepository.save(location);

        return mapToResponse(location);
    }

    @Override
    public LocationResponse update(Long id, LocationRequest request) {

        Location location = locationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location not found."));

        if (!location.getName().equals(request.getName())
                && locationRepository.existsByName(request.getName())) {

            throw new DuplicateResourceException("Location with this name already exists.");
        }

        location.setName(request.getName());

        locationRepository.save(location);

        return mapToResponse(location);
    }

    @Override
    public LocationResponse getById(Long id) {

        Location location = locationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location not found."));

        return mapToResponse(location);
    }

    @Override
    public List<LocationResponse> getAll() {

        return locationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Location location = locationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Location not found."));

        locationRepository.delete(location);
    }


    private LocationResponse mapToResponse(Location location) {

        return LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .build();
    }
}
