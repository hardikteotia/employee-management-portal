import { NavLink } from 'react-router-dom'

// Navigation items for the portal. `end` is used on Dashboard so it
// doesn't stay highlighted on nested routes.
const navItems = [
  { to: '/', label: 'Dashboard', end: true },
  { to: '/employees', label: 'Employees' },
  { to: '/departments', label: 'Departments' },
  { to: '/projects', label: 'Projects' },
  { to: '/employee-projects', label: 'Employee Projects' },
  { to: '/attendance', label: 'Attendance' },
  { to: '/leaves', label: 'Leave Management' },
  { to: '/announcements', label: 'Announcements' },
  { to: '/profile', label: 'Profile' },
  { to: '/settings', label: 'Settings' },
]

export default function Sidebar() {
  return (
    <aside className="flex h-full w-64 flex-col border-r border-ink-border bg-ink-surface">
      {/* Brand */}
      <div className="flex h-16 items-center gap-2 border-b border-ink-border px-6">
        <span className="text-lg font-semibold tracking-wide text-gold">
          LANEWAY
        </span>
        <span className="text-xs font-medium uppercase tracking-wider text-gray-500">
          EMP
        </span>
      </div>

      {/* Links */}
      <nav className="flex-1 space-y-1 overflow-y-auto px-3 py-4">
        {navItems.map((item) => (
          <NavLink
            key={item.to}
            to={item.to}
            end={item.end}
            className={({ isActive }) =>
              `block rounded-md px-3 py-2 text-sm font-medium transition-colors ${
                isActive
                  ? 'bg-gold/10 text-gold'
                  : 'text-gray-400 hover:bg-ink-raised hover:text-gray-100'
              }`
            }
          >
            {item.label}
          </NavLink>
        ))}
      </nav>

      <div className="border-t border-ink-border px-6 py-4 text-xs text-gray-600">
        v0.1 · Internal
      </div>
    </aside>
  )
}
