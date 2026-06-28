import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext.jsx'
import Button from './Button.jsx'

export default function Navbar() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login', { replace: true })
  }

  // First letter of the username for the avatar circle.
  const initial = user?.username?.charAt(0)?.toUpperCase() || '?'

  return (
    <header className="flex h-16 items-center justify-between border-b border-ink-border bg-ink-surface px-6">
      <h1 className="text-sm font-medium uppercase tracking-wider text-gray-400">
        Employee Management Portal
      </h1>

      <div className="flex items-center gap-4">
        <div className="flex items-center gap-3">
          <div className="flex h-9 w-9 items-center justify-center rounded-full bg-gold/15 text-sm font-semibold text-gold">
            {initial}
          </div>
          <div className="hidden text-right sm:block">
            <div className="text-sm font-medium text-gray-100">
              {user?.username || 'Unknown'}
            </div>
            <div className="text-xs text-gray-500">{user?.role || '—'}</div>
          </div>
        </div>

        <Button variant="secondary" onClick={handleLogout}>
          Logout
        </Button>
      </div>
    </header>
  )
}
