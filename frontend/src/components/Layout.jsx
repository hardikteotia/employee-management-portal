import { Outlet } from 'react-router-dom'
import Sidebar from './Sidebar.jsx'
import Navbar from './Navbar.jsx'

// The authenticated app shell: fixed sidebar on the left, navbar on top,
// scrollable content area filling the rest. Child routes render in <Outlet />.
export default function Layout() {
  return (
    <div className="flex h-screen overflow-hidden bg-ink">
      <Sidebar />
      <div className="flex flex-1 flex-col overflow-hidden">
        <Navbar />
        <main className="flex-1 overflow-y-auto p-6">
          <Outlet />
        </main>
      </div>
    </div>
  )
}
