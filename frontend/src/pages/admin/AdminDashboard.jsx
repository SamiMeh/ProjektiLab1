import { useEffect, useState } from 'react';
import { Card, Col, Row, Spinner } from 'react-bootstrap';
import { Auth, API, unwrapList } from '../../api/client';

export default function AdminDashboard() {
  const user = Auth.getUser();
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({
    kandidatet: 0,
    regjistrime: 0,
    instruktoret: 0,
    automjetet: 0,
    oretTeori: 0,
    oretPraktike: 0,
  });

  useEffect(() => {
    async function load() {
      setLoading(true);
      try {
        const [kand, reg, inst, auto, ot, op] = await Promise.allSettled([
          API.kandidatet.getAll(),
          API.regjistrime.getAll(),
          API.instruktoret.getAll(),
          API.automjetet.getAll(),
          API.oreteTeoria.getAll(),
          API.oretPraktike.getAll(),
        ]);
        const len = (r) => unwrapList(r.status === 'fulfilled' ? r.value : []).length;
        setStats({
          kandidatet: len(kand),
          regjistrime: len(reg),
          instruktoret: len(inst),
          automjetet: len(auto),
          oretTeori: len(ot),
          oretPraktike: len(op),
        });
      } finally {
        setLoading(false);
      }
    }
    load();
  }, []);

  const today = new Date().toLocaleDateString('sq-AL', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  });

  if (loading) {
    return (
      <div className="page-loading">
        <Spinner animation="border" style={{ color: 'var(--brand)' }} />
      </div>
    );
  }

  const cards = [
    { label: 'Kandidatë', value: stats.kandidatet, icon: 'bi-people', color: '#ef4444' },
    { label: 'Regjistrime', value: stats.regjistrime, icon: 'bi-journal-text', color: '#3b82f6' },
    { label: 'Instruktorë', value: stats.instruktoret, icon: 'bi-person-badge', color: '#10b981' },
    { label: 'Automjete', value: stats.automjetet, icon: 'bi-truck', color: '#f59e0b' },
    { label: 'Orë teorie', value: stats.oretTeori, icon: 'bi-book', color: '#06b6d4' },
    { label: 'Orë praktike', value: stats.oretPraktike, icon: 'bi-car-front', color: '#8b5cf6' },
  ];

  return (
    <div>
      <Card className="border-0 text-white mb-4 brand-gradient">
        <Card.Body className="p-4">
          <h3 className="fw-bold mb-1">Mirë se erdhe, {user?.firstName || 'Miku'} 👋</h3>
          <p className="mb-1 opacity-85">Kontrollo aktivitetin e sotshëm të autoshkollës</p>
          <small className="opacity-75">{today}</small>
        </Card.Body>
      </Card>

      <Row className="g-3">
        {cards.map((c) => (
          <Col key={c.label} sm={6} xl={4}>
            <Card className="stat-card h-100">
              <Card.Body className="d-flex align-items-center gap-3">
                <span
                  className="rounded-3 d-flex align-items-center justify-content-center text-white flex-shrink-0"
                  style={{ width: 48, height: 48, background: c.color }}
                >
                  <i className={`bi ${c.icon} fs-5`} />
                </span>
                <div>
                  <div className="fs-3 fw-bold">{c.value}</div>
                  <div className="text-muted small">{c.label}</div>
                </div>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
}
