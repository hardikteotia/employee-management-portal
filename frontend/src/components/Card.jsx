// Simple panel/card surface. Used for dashboard tiles, form wrappers,
// table containers, etc.
export default function Card({ children, className = '' }) {
  return (
    <div
      className={`rounded-lg border border-ink-border bg-ink-surface p-5 ${className}`}
    >
      {children}
    </div>
  )
}
