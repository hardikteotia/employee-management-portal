import { createContext, useContext, useEffect, useState } from 'react'

const AuthContext = createContext(null)

// Decode the payload of a JWT without verifying it (verification is the
// backend's job). We only use this to read the username/role for display.
function decodeToken(token) {
  try {
    const payload = token.split('.')[1]
    const json = atob(payload.replace(/-/g, '+').replace(/_/g, '/'))
    return JSON.parse(json)
  } catch {
    return null
  }
}

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('token'))

  // Derived user info from the token (username = JWT subject).
  const claims = token ? decodeToken(token) : null
  const user = claims
    ? { username: claims.sub, role: claims.role || null }
    : null

  useEffect(() => {
    if (token) {
      localStorage.setItem('token', token)
    } else {
      localStorage.removeItem('token')
    }
  }, [token])

  const login = (newToken) => setToken(newToken)
  const logout = () => setToken(null)

  const value = {
    token,
    user,
    isAuthenticated: Boolean(token),
    login,
    logout,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

// eslint-disable-next-line react-refresh/only-export-components
export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  return ctx
}
