package com.laneway.empportal.service;

import com.laneway.empportal.dto.request.AnnouncementRequest;
import com.laneway.empportal.dto.response.AnnouncementResponse;

import java.util.List;

public interface AnnouncementService {

    AnnouncementResponse create(AnnouncementRequest request);

    AnnouncementResponse update(Long id, AnnouncementRequest request);

    AnnouncementResponse getById(Long id);

    List<AnnouncementResponse> getAll();

    void delete(Long id);
}
