package com.laneway.empportal.serviceImplementation;

import com.laneway.empportal.dto.request.AnnouncementRequest;
import com.laneway.empportal.dto.response.AnnouncementResponse;
import com.laneway.empportal.entity.Announcement;
import com.laneway.empportal.exception.ResourceNotFoundException;
import com.laneway.empportal.repository.AnnouncementRepository;
import com.laneway.empportal.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Override
    public AnnouncementResponse create(AnnouncementRequest request) {

        Announcement announcement = Announcement.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdBy(request.getCreatedBy())
                .createdDate(LocalDate.now())
                .build();

        announcementRepository.save(announcement);

        return mapToResponse(announcement);
    }

    @Override
    public AnnouncementResponse update(Long id, AnnouncementRequest request) {

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Announcement not found."));

        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setCreatedBy(request.getCreatedBy());

        announcementRepository.save(announcement);

        return mapToResponse(announcement);
    }

    @Override
    public AnnouncementResponse getById(Long id) {

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Announcement not found."));

        return mapToResponse(announcement);
    }

    @Override
    public List<AnnouncementResponse> getAll() {

        return announcementRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Announcement not found."));

        announcementRepository.delete(announcement);
    }


    private AnnouncementResponse mapToResponse(Announcement announcement) {

        return AnnouncementResponse.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .createdBy(announcement.getCreatedBy())
                .createdDate(announcement.getCreatedDate())
                .build();
    }
}
