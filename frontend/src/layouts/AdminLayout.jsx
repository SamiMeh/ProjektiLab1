import { Button, Nav } from 'react-bootstrap';
import { NavLink as RRNavLink, Outlet, useNavigate } from 'react-router-dom';
import { API, Auth } from '../api/client';
import BrandLogo from '../components/BrandLogo';

const ADMIN_NAV = [
  { section: 'Kryefaqja', items: [{ to: '/admin', end: true, icon: 'bi-speedometer2', label: 'Paneli kryesor' }] },
  {
    section: 'Administrim',
    adminOnly: true,
    items: [
      { to: '/admin/kandidatet', icon: 'bi-people', label: 'Kandidatët' },
      { to: '/admin/kategorite', icon: 'bi-card-list', label: 'Kategoritë' },
      { to: '/admin/regjistrime', icon: 'bi-journal-text', label: 'Regjistrimet' },
      { to: '/admin/instruktoret', icon: 'bi-person-badge', label: 'Instruktorët' },
      { to: '/admin/automjetet', icon: 'bi-truck', label: 'Automjetet' },
      { to: '/admin/pagesat', icon: 'bi-credit-card', label: 'Pagesat' },
    ],
  },
  {
    section: 'Mësimdhënia',
    items: [
      { to: '/admin/ore-teorie', icon: 'bi-book', label: 'Orët e Teorisë' },
      { to: '/admin/ore-praktike', icon: 'bi-car-front', label: 'Orët e Praktikës' },
      { to: '/admin/provimet', icon: 'bi-trophy', label: 'Provimet' },
      { to: '/admin/oraret', icon: 'bi-calendar3', label: 'Oraret' },
    ],
  },
];

export default function AdminLayout() {
  const user = Auth.getUser();
  const navigate = useNavigate();
  const isAdmin = Auth.isAdmin();
  const initials = `${user?.firstName?.[0] || 'A'}${user?.lastName?.[0] || 'D'}`.toUpperCase();
  const roleLabel = isAdmin ? 'Administrator' : 'Instruktor';

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
    <div className="d-flex min-vh-100">
      <aside className="admin-sidebar d-flex flex-column p-3 flex-shrink-0">
        <div className="mb-4 px-1">
          <BrandLogo height={44} subtitle={roleLabel} darkText />
        </div>

        <Nav className="flex-column flex-grow-1 gap-1">
          {ADMIN_NAV.filter((g) => !g.adminOnly || isAdmin).map((group) => (
            <div key={group.section} className="mb-3">
              <div className="text-uppercase text-muted small fw-semibold px-2 mb-2" style={{ fontSize: '0.65rem', letterSpacing: '0.06em' }}>
                {group.section}
              </div>
              {group.items.map((item) => (
                <Nav.Link
                  key={item.to}
                  as={RRNavLink}
                  to={item.to}
                  end={item.end}
                  className="d-flex align-items-center gap-2"
                >
                  <i className={`bi ${item.icon}`} />
                  {item.label}
                </Nav.Link>
              ))}
            </div>
          ))}
        </Nav>

        <div className="border-top pt-3 mt-2 d-flex align-items-center gap-2">
          <span
            className="rounded-circle d-inline-flex align-items-center justify-content-center fw-bold flex-shrink-0"
            style={{ width: 40, height: 40, background: '#fff4eb', color: 'var(--brand)' }}
          >
            {initials}
          </span>
          <div className="flex-grow-1 overflow-hidden">
            <div className="fw-semibold small text-truncate">
              {user?.firstName} {user?.lastName}
            </div>
            <div className="text-muted text-truncate" style={{ fontSize: '0.7rem' }}>
              {user?.email}
            </div>
          </div>
          <Button variant="link" className="text-secondary p-0" onClick={logout} title="Dil">
            <i className="bi bi-box-arrow-right" />
          </Button>
        </div>
      </aside>

      <main className="flex-grow-1 p-4 overflow-auto">
        <Outlet />
      </main>
    </div>
  );
}
