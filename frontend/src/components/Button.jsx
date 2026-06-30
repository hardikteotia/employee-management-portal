// Reusable button with a few corporate variants. No fancy effects —
// just clean fills, a hover shade, and a disabled state.
export default function Button({
  children,
  variant = 'primary',
  type = 'button',
  className = '',
  disabled = false,
  ...props
}) {
  const base =
    'inline-flex items-center justify-center rounded-md px-4 py-2 text-sm font-medium ' +
    'transition-colors focus:outline-none focus:ring-1 focus:ring-gold ' +
    'disabled:cursor-not-allowed disabled:opacity-50'

  const variants = {
    primary: 'bg-gold text-black hover:bg-gold-light',
    secondary:
      'border border-ink-border bg-ink-raised text-gray-200 hover:bg-ink-border',
    danger: 'bg-red-600 text-white hover:bg-red-500',
    ghost: 'text-gray-300 hover:bg-ink-raised',
  }

  return (
    <button
      type={type}
      disabled={disabled}
      className={`${base} ${variants[variant]} ${className}`}
      {...props}
    >
      {children}
    </button>
  )
}
