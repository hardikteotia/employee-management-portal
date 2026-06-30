package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.LocationRequest;
import com.laneway.empportal.dto.response.LocationResponse;

import java.util.List;

public interface LocationService {

    LocationResponse create(LocationRequest request);

    LocationResponse update(Long id, LocationRequest request);

    LocationResponse getById(Long id);

    List<LocationResponse> getAll();

    void delete(Long id);
}
