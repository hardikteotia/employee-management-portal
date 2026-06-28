// Temporary stand-in for pages that haven't been built yet, so the
// sidebar links all resolve to something instead of a blank screen.
export default function Placeholder({ title }) {
  return (
    <div>
      <h2 className="text-xl font-semibold text-gray-100">{title}</h2>
      <p className="mt-2 text-sm text-gray-500">
        This page is coming soon. We'll build it out next.
      </p>
    </div>
  )
}
