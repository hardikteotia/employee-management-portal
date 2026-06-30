import { Navigate, useLocation } from 'react-router-dom'
import { useAuth } from '../context/AuthContext.jsx'

// Wraps protected routes. If there's no token, redirect to /login and
// remember where the user was trying to go. If `allowedRoles` is given,
// an authenticated user without a matching role is sent back to the
// dashboard (so a hidden link can't be reached by typing the URL).
export default function ProtectedRoute({ children, allowedRoles }) {
  const { isAuthenticated, user } = useAuth()
  const location = useLocation()

  if (!isAuthenticated) {
    return <Navigate to="/login" replace state={{ from: location }} />
  }

  if (allowedRoles && !allowedRoles.includes(user?.role)) {
    return <Navigate to="/" replace />
  }

  return children
}
