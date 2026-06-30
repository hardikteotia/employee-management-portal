import { useEffect, useState } from 'react'
import api from '../api/axios.js'
import Card from '../components/Card.jsx'
import { useAuth } from '../context/AuthContext.jsx'
import {
  DASHBOARD_CARDS,
  DASHBOARD_SUBTITLES,
  allowedFor,
} from '../config/roles.js'

export default function Dashboard() {
  const { user } = useAuth()
  const cards = allowedFor(DASHBOARD_CARDS, user?.role)
  const subtitle =
    DASHBOARD_SUBTITLES[user?.role] || 'Overview of your organization.'

  const [summary, setSummary] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    let active = true
    api
      .get('/dashboard/summary')
      .then(({ data }) => {
        if (active) setSummary(data)
      })
      .catch((err) => {
        if (active) {
          setError(
            err.response?.data?.message ||
              'Failed to load dashboard data.',
          )
        }
      })
      .finally(() => {
        if (active) setLoading(false)
      })
    return () => {
      active = false
    }
  }, [])

  return (
    <div>
      <div className="mb-6">
        <h2 className="text-xl font-semibold text-gray-100">Dashboard</h2>
        <p className="mt-1 text-sm text-gray-500">{subtitle}</p>
      </div>

      {error && (
        <div className="mb-6 rounded-md border border-red-900 bg-red-950/50 px-4 py-3 text-sm text-red-300">
          {error}
        </div>
      )}

      <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5">
        {cards.map((stat) => (
          <Card key={stat.key}>
            <div className="text-sm font-medium text-gray-400">
              {stat.label}
            </div>
            <div className="mt-2 text-3xl font-semibold text-gold">
              {loading ? (
                <span className="text-gray-600">—</span>
              ) : (
                (summary?.[stat.key] ?? 0)
              )}
            </div>
          </Card>
        ))}
      </div>
    </div>
  )
}
