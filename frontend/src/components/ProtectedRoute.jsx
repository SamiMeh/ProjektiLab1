import { Navigate } from 'react-router-dom';
import { Auth } from '../api/client';

export default function ProtectedRoute({ children, roles }) {
  if (!Auth.isLoggedIn()) {
    return <Navigate to="/login" replace />;
  }

  const userRole = Auth.getUser()?.role || '';
  if (roles && roles.length > 0 && !roles.includes(userRole)) {
    if (Auth.isAdmin() || Auth.isInstruktor()) {
      return <Navigate to="/admin" replace />;
    }
    return <Navigate to="/kandidat" replace />;
  }

  return children;
}
