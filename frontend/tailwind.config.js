/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        // Gold accent inspired by the Laneway branding.
        // Change these in one place to retune the whole app.
        gold: {
          DEFAULT: '#C5A253',
          light: '#D8BE78',
          dark: '#A07E32',
        },
        // Dark corporate surfaces.
        ink: {
          DEFAULT: '#0A0A0A', // page background (black)
          surface: '#141414', // cards / panels
          raised: '#1C1C1C', // hover / inputs
          border: '#2A2A2A', // hairline borders
        },
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'Segoe UI', 'Roboto', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
