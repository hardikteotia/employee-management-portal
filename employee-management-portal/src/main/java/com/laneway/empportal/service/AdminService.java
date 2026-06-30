package com.laneway.empportal.service;

import com.laneway.empportal.dto.response.AdminDashboardResponse;

public interface AdminService {

    /**
     * Builds the organisation-wide summary shown on the admin dashboard.
     */
    AdminDashboardResponse getDashboard();
}
