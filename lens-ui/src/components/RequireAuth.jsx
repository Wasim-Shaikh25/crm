import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import Cookies from 'js-cookie';

export default function RequireAuth({ children }) {
  const token = Cookies.get('access_token');
  const location = useLocation();
  if (!token || token === 'null') {
    return <Navigate to="/login" replace state={{ from: location }} />;
  }
  return children;
}
