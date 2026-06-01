import { Container, Nav, Navbar } from 'react-bootstrap';
import { NavLink as RRNavLink, Outlet, useNavigate } from 'react-router-dom';
import { API, Auth } from '../api/client';
import BrandLogo from '../components/BrandLogo';

const NAV = [
  { to: '/kandidat', end: true, icon: 'bi-speedometer2', label: 'Paneli Kryesor' },
  { to: '/kandidat/oraret', icon: 'bi-calendar3', label: 'Oraret e Mia' },
  { to: '/kandidat/ore-teorie', icon: 'bi-book', label: 'Orët e Teorisë' },
  { to: '/kandidat/ore-praktike', icon: 'bi-car-front', label: 'Orët e Praktikës' },
  { to: '/kandidat/provimet', icon: 'bi-trophy', label: 'Provimet e Mia' },
  { to: '/kandidat/pagesat', icon: 'bi-cash-coin', label: 'Pagesat e Mia' },
];

export default function KandidatLayout() {
  const user = Auth.getUser();
  const navigate = useNavigate();
  const initials = `${user?.firstName?.[0] || 'K'}${user?.lastName?.[0] || 'A'}`.toUpperCase();

  async function logout() {
    try {
      await API.auth.logout();
    } catch {
      /* ignore */
    }
    Auth.clear();
    navigate('/login');
  }

  return (
    <div className="min-vh-100 bg-light">
      <Navbar bg="white" className="border-bottom shadow-sm sticky-top">
        <Container fluid className="px-3 px-md-4">
          <Navbar.Brand className="p-0">
            <BrandLogo height={38} showText darkText />
          </Navbar.Brand>
          <div className="d-flex align-items-center gap-2">
            <span
              className="rounded-circle d-inline-flex align-items-center justify-content-center fw-bold"
              style={{ width: 36, height: 36, background: '#fff4eb', color: 'var(--brand)', border: '1px solid #fed7aa' }}
            >
              {initials}
            </span>
            <span className="d-none d-sm-inline fw-semibold text-secondary small">
              {user?.firstName || 'Kandidat'}
            </span>
            <button type="button" className="btn btn-link text-secondary p-1" onClick={logout} title="Dil">
              <i className="bi bi-box-arrow-right fs-5" />
            </button>
          </div>
        </Container>
      </Navbar>

      <Container fluid className="px-3 px-md-4 py-2 bg-white border-bottom">
        <Nav variant="pills" className="kandidat-nav-pills flex-nowrap overflow-auto pb-1">
          {NAV.map((item) => (
            <Nav.Item key={item.to}>
              <Nav.Link
                as={RRNavLink}
                to={item.to}
                end={item.end}
                className="d-inline-flex align-items-center gap-1"
              >
                <i className={`bi ${item.icon}`} />
                {item.label}
              </Nav.Link>
            </Nav.Item>
          ))}
        </Nav>
      </Container>

      <Container fluid className="px-3 px-md-4 py-4" style={{ maxWidth: 1200 }}>
        <Outlet />
      </Container>
    </div>
  );
}
