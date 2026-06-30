// Single source of truth for role-based navigation and dashboard content.
// The JWT `role` claim (decoded in AuthContext) is one of these values.
export const ROLES = {
  ADMIN: 'ADMIN',
  MANAGER: 'MANAGER',
  EMPLOYEE: 'EMPLOYEE',
}

// Convenience for items every authenticated role can see.
const ALL = [ROLES.ADMIN, ROLES.MANAGER, ROLES.EMPLOYEE]

// Sidebar navigation. `end` keeps Dashboard from staying highlighted on
// nested routes. `roles` lists who may see (and reach) each item.
export const NAV_ITEMS = [
  { to: '/', label: 'Dashboard', end: true, roles: ALL },
  { to: '/employees', label: 'Employees', roles: [ROLES.ADMIN, ROLES.MANAGER] },
  { to: '/departments', label: 'Departments', roles: [ROLES.ADMIN] },
  { to: '/projects', label: 'Projects', roles: ALL },
  {
    to: '/employee-projects',
    label: 'Employee Projects',
    roles: [ROLES.ADMIN, ROLES.MANAGER],
  },
  { to: '/attendance', label: 'Attendance', roles: ALL },
  { to: '/leaves', label: 'Leave Management', roles: ALL },
  { to: '/announcements', label: 'Announcements', roles: ALL },
  { to: '/profile', label: 'Profile', roles: ALL },
  { to: '/settings', label: 'Settings', roles: [ROLES.ADMIN] },
]

// Dashboard stat cards. `key` maps to a DashboardSummaryResponse field.
export const DASHBOARD_CARDS = [
  {
    key: 'totalEmployees',
    label: 'Total Employees',
    roles: [ROLES.ADMIN, ROLES.MANAGER],
  },
  {
    key: 'activeEmployees',
    label: 'Active Employees',
    roles: [ROLES.ADMIN, ROLES.MANAGER],
  },
  {
    key: 'employeesOnLeave',
    label: 'On Leave',
    roles: [ROLES.ADMIN, ROLES.MANAGER],
  },
  { key: 'exitedEmployees', label: 'Exited Employees', roles: [ROLES.ADMIN] },
  { key: 'totalDepartments', label: 'Departments', roles: [ROLES.ADMIN] },
  { key: 'totalLocations', label: 'Locations', roles: [ROLES.ADMIN] },
  { key: 'totalProjects', label: 'Projects', roles: ALL },
  {
    key: 'remoteEmployees',
    label: 'Remote Employees',
    roles: [ROLES.ADMIN, ROLES.MANAGER],
  },
]

// Role-aware dashboard subtitles.
export const DASHBOARD_SUBTITLES = {
  [ROLES.ADMIN]: 'Overview of your organization.',
  [ROLES.MANAGER]: 'Overview of your team.',
  [ROLES.EMPLOYEE]: 'Your workspace at a glance.',
}

// Filters a list of role-tagged items for the given role. An unknown or
// missing role yields an empty list (fail closed rather than leaking access).
export function allowedFor(items, role) {
  if (!role) return []
  return items.filter((item) => item.roles.includes(role))
}
