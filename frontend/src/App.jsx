import { Navigate, Route, Routes } from 'react-router-dom';
import { Auth } from './api/client';
import LoginPage from './pages/LoginPage';
import ProtectedRoute from './components/ProtectedRoute';
import KandidatLayout from './layouts/KandidatLayout';
import AdminLayout from './layouts/AdminLayout';
import KandidatDashboard from './pages/kandidat/KandidatDashboard';
import KandidatListPage from './pages/kandidat/KandidatListPage';
import AdminDashboard from './pages/admin/AdminDashboard';
import CrudPage from './pages/admin/CrudPage';

function HomeRedirect() {
  if (!Auth.isLoggedIn()) return <Navigate to="/login" replace />;
  if (Auth.isAdmin() || Auth.isInstruktor()) return <Navigate to="/admin" replace />;
  return <Navigate to="/kandidat" replace />;
}

const ADMIN_ENTITIES = [
  'kandidatet',
  'kategorite',
  'regjistrime',
  'instruktoret',
  'automjetet',
  'pagesat',
];

const SHARED_ENTITIES = ['ore-teorie', 'ore-praktike', 'provimet', 'oraret'];

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/" element={<HomeRedirect />} />

      <Route
        path="/kandidat"
        element={
          <ProtectedRoute roles={['USER', 'ROLE_USER']}>
            <KandidatLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<KandidatDashboard />} />
        <Route path="oraret" element={<KandidatListPage type="oraret" />} />
        <Route path="ore-teorie" element={<KandidatListPage type="ore-teorie" />} />
        <Route path="ore-praktike" element={<KandidatListPage type="ore-praktike" />} />
        <Route path="provimet" element={<KandidatListPage type="provimet" />} />
        <Route path="pagesat" element={<KandidatListPage type="pagesat" />} />
      </Route>

      <Route
        path="/admin"
        element={
          <ProtectedRoute roles={['ADMIN', 'INSTRUKTOR']}>
            <AdminLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<AdminDashboard />} />
        {ADMIN_ENTITIES.map((entity) => (
          <Route key={entity} path={entity} element={<CrudPage entity={entity} />} />
        ))}
        {SHARED_ENTITIES.map((entity) => (
          <Route key={entity} path={entity} element={<CrudPage entity={entity} />} />
        ))}
      </Route>

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
