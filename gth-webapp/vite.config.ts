import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    port: 4200,
    proxy: {
      '/identity': 'http://localhost:8085',
      '/recruitment': 'http://localhost:8085',
      '/contract': 'http://localhost:8085',
    },
  },
});
