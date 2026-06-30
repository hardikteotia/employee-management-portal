import { Routes, Route, Navigate } from 'react-router-dom'
import ProtectedRoute from './components/ProtectedRoute.jsx'
import Layout from './components/Layout.jsx'
import Login from './pages/Login.jsx'
import Dashboard from './pages/Dashboard.jsx'
import Placeholder from './pages/Placeholder.jsx'
import { ROLES } from './config/roles.js'

export default function App() {
  return (
    <Routes>
      {/* Public */}
      <Route path="/login" element={<Login />} />

      {/* Protected app shell */}
      <Route
        element={
          <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route path="/" element={<Dashboard />} />
        <Route
          path="/employees"
          element={
            <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
              <Placeholder title="Employees" />
            </ProtectedRoute>
          }
        />
        <Route
          path="/departments"
          element={
            <ProtectedRoute allowedRoles={[ROLES.ADMIN]}>
              <Placeholder title="Departments" />
            </ProtectedRoute>
          }
        />
        <Route path="/projects" element={<Placeholder title="Projects" />} />
        <Route
          path="/employee-projects"
          element={
            <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
              <Placeholder title="Employee Projects" />
            </ProtectedRoute>
          }
        />
        <Route
          path="/attendance"
          element={<Placeholder title="Attendance" />}
        />
        <Route
          path="/leaves"
          element={<Placeholder title="Leave Management" />}
        />
        <Route
          path="/announcements"
          element={<Placeholder title="Announcements" />}
        />
        <Route path="/profile" element={<Placeholder title="Profile" />} />
        <Route
          path="/settings"
          element={
            <ProtectedRoute allowedRoles={[ROLES.ADMIN]}>
              <Placeholder title="Settings" />
            </ProtectedRoute>
          }
        />
      </Route>

      {/* Fallback */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}
