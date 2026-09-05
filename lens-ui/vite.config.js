import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
import { defineConfig } from 'vite'
import path from 'node:path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()],
  resolve: {
    alias: { '@': path.resolve(import.meta.dirname, './src') },
  },
  server: {
    port: 3000,
    proxy: {
      // Forward API calls to the Spring Boot backend in dev.
      // Regex keys so UI routes like /users or /login aren't swallowed:
      // backend uses /lens/*, /user/*, /auth/* (with trailing path segments).
      '^/lens/': 'http://localhost:8080',
      '^/user/': 'http://localhost:8080',
      '^/auth/': 'http://localhost:8080',
    },
  },
})
