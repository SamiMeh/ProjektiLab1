import { useState } from 'react';
import {
  Alert,
  Button,
  Card,
  Col,
  Container,
  Form,
  Modal,
  Row,
  Tab,
  Tabs,
} from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { API, Auth, normalizeLoginResponse } from '../api/client';
import BrandLogo from '../components/BrandLogo';

export default function LoginPage() {
  const navigate = useNavigate();
  const [tab, setTab] = useState('login');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [staffModal, setStaffModal] = useState(null);

  const [loginEmail, setLoginEmail] = useState('');
  const [loginPassword, setLoginPassword] = useState('');
  const [reg, setReg] = useState({ firstName: '', lastName: '', email: '', password: '' });
  const [staff, setStaff] = useState({ email: '', password: '' });

  if (Auth.isLoggedIn()) {
    if (Auth.isAdmin() || Auth.isInstruktor()) navigate('/admin', { replace: true });
    else navigate('/kandidat', { replace: true });
  }

  function redirectByRole(role) {
    if (role === 'ADMIN' || role === 'INSTRUKTOR') navigate('/admin');
    else navigate('/kandidat');
  }

  async function handleLogin(e) {
    e?.preventDefault();
    setError('');
    setLoading(true);
    try {
      const res = await API.auth.login({ email: loginEmail.trim(), password: loginPassword });
      const { accessToken, user, role } = normalizeLoginResponse(res);
      Auth.setSession(accessToken, user);
      redirectByRole(role);
    } catch (err) {
      setError(err.message || 'Email ose fjalëkalim i gabuar.');
    } finally {
      setLoading(false);
    }
  }

  async function handleRegister(e) {
    e?.preventDefault();
    setError('');
    if (!reg.firstName || !reg.lastName || !reg.email || !reg.password) {
      setError('Plotëso të gjitha fushat.');
      return;
    }
    setLoading(true);
    try {
      await API.auth.register(reg);
      setTab('login');
      setLoginEmail(reg.email);
      setError('');
      alert('Llogaria u krijua! Hyr tani.');
    } catch (err) {
      setError(err.message || 'Gabim gjatë regjistrimit.');
    } finally {
      setLoading(false);
    }
  }

  async function handleStaffLogin(e) {
    e?.preventDefault();
    setError('');
    setLoading(true);
    try {
      const res = await API.auth.login({ email: staff.email.trim(), password: staff.password });
      const { accessToken, user, role } = normalizeLoginResponse(res);
      if (staffModal === 'admin' && role !== 'ADMIN') {
        throw new Error('Ky llogari nuk është Administrator.');
      }
      if (staffModal === 'instruktor' && role !== 'INSTRUKTOR') {
        throw new Error('Ky llogari nuk është Instruktor.');
      }
      Auth.setSession(accessToken, user);
      setStaffModal(null);
      redirectByRole(role);
    } catch (err) {
      setError(err.message || 'Email ose fjalëkalim i gabuar.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="min-vh-100 row g-0 flex-grow-1">
      <div className="d-none d-lg-flex col-lg-6 login-hero flex-column justify-content-between p-5">
        <BrandLogo height={56} subtitle="Sistemi i menaxhimit" />
        <div>
          <h2 className="fw-bold display-6">Menaxho autoshkollën tënde me lehtësi</h2>
          <p className="opacity-85 mt-3">Kandidatë, instruktorë, orare, pagesa — gjithçka në një vend.</p>
        </div>
        <div />
      </div>

      <div className="col-12 col-lg-6 d-flex align-items-center justify-content-center p-4 bg-light">
        <Card className="border-0 shadow-sm w-100" style={{ maxWidth: 420 }}>
          <Card.Body className="p-4">
            <h3 className="fw-bold mb-1">Mirë se erdhe!</h3>
            <p className="text-muted small mb-4">Hyr në llogarinë tënde për të vazhduar</p>

            {error && tab !== 'staff' && <Alert variant="danger" className="py-2 small">{error}</Alert>}

            <Tabs activeKey={tab} onSelect={(k) => { setTab(k); setError(''); }} className="mb-3">
              <Tab eventKey="login" title="Hyrja">
                <Form onSubmit={handleLogin}>
                  <Form.Group className="mb-3">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                      type="email"
                      value={loginEmail}
                      onChange={(e) => setLoginEmail(e.target.value)}
                      placeholder="shembull@email.com"
                      required
                    />
                  </Form.Group>
                  <Form.Group className="mb-3">
                    <Form.Label>Fjalëkalimi</Form.Label>
                    <Form.Control
                      type="password"
                      value={loginPassword}
                      onChange={(e) => setLoginPassword(e.target.value)}
                      required
                    />
                  </Form.Group>
                  <Button type="submit" variant="primary" className="w-100 mb-3" style={{ background: 'var(--brand)', borderColor: 'var(--brand)' }} disabled={loading}>
                    {loading ? 'Duke hyrë...' : 'Hyr si Kandidat'}
                  </Button>
                </Form>

                <div className="text-center text-muted small my-3">Hyrje për staf</div>
                <Row className="g-2">
                  <Col>
                    <Button variant="outline-secondary" className="w-100" onClick={() => { setStaffModal('admin'); setStaff({ email: '', password: '' }); setError(''); }}>
                      🛡️ Administrator
                    </Button>
                  </Col>
                  <Col>
                    <Button variant="outline-secondary" className="w-100" onClick={() => { setStaffModal('instruktor'); setStaff({ email: '', password: '' }); setError(''); }}>
                      🧑‍🏫 Instruktor
                    </Button>
                  </Col>
                </Row>
              </Tab>

              <Tab eventKey="register" title="Regjistrohu">
                <Form onSubmit={handleRegister}>
                  <Row>
                    <Col>
                      <Form.Group className="mb-3">
                        <Form.Label>Emri</Form.Label>
                        <Form.Control value={reg.firstName} onChange={(e) => setReg({ ...reg, firstName: e.target.value })} required />
                      </Form.Group>
                    </Col>
                    <Col>
                      <Form.Group className="mb-3">
                        <Form.Label>Mbiemri</Form.Label>
                        <Form.Control value={reg.lastName} onChange={(e) => setReg({ ...reg, lastName: e.target.value })} required />
                      </Form.Group>
                    </Col>
                  </Row>
                  <Form.Group className="mb-3">
                    <Form.Label>Email</Form.Label>
                    <Form.Control type="email" value={reg.email} onChange={(e) => setReg({ ...reg, email: e.target.value })} required />
                  </Form.Group>
                  <Form.Group className="mb-3">
                    <Form.Label>Fjalëkalimi</Form.Label>
                    <Form.Control type="password" value={reg.password} onChange={(e) => setReg({ ...reg, password: e.target.value })} required />
                  </Form.Group>
                  <Button type="submit" variant="primary" className="w-100" style={{ background: 'var(--brand)', borderColor: 'var(--brand)' }} disabled={loading}>
                    Krijo llogari
                  </Button>
                </Form>
              </Tab>
            </Tabs>
          </Card.Body>
        </Card>
      </div>

      <Modal show={!!staffModal} onHide={() => setStaffModal(null)} centered>
        <Modal.Header closeButton>
          <Modal.Title>
            {staffModal === 'admin' ? '🛡️ Hyrje si Administrator' : '🧑‍🏫 Hyrje si Instruktor'}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleStaffLogin}>
          <Modal.Body>
            {error && staffModal && <Alert variant="danger" className="py-2 small">{error}</Alert>}
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control type="email" value={staff.email} onChange={(e) => setStaff({ ...staff, email: e.target.value })} required />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Fjalëkalimi</Form.Label>
              <Form.Control type="password" value={staff.password} onChange={(e) => setStaff({ ...staff, password: e.target.value })} required />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => setStaffModal(null)}>Anulo</Button>
            <Button type="submit" style={{ background: 'var(--brand)', borderColor: 'var(--brand)' }} disabled={loading}>
              Hyr
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </div>
  );
}
