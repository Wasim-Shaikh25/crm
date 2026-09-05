import axios from 'axios';
import Cookies from 'js-cookie';
import { toast } from 'sonner';

const api = axios.create({
  // Vite dev proxy forwards /lens /user /auth to the backend.
  // Set VITE_API_URL to point at a deployed backend instead.
  baseURL: import.meta.env.VITE_API_URL || '',
  timeout: 30000,
});

api.interceptors.request.use((config) => {
  const token = Cookies.get('access_token');
  if (token && token !== 'null') {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (res) => res,
  (error) => {
    const status = error.response?.status;
    if (status === 401) {
      Cookies.remove('access_token');
      if (window.location.pathname !== '/login') window.location.href = '/login';
    }
    const message =
      error.response?.data?.message ||
      error.response?.data?.error ||
      error.message ||
      'Request failed';
    toast.error(message);
    return Promise.reject(error);
  },
);

export default api;
