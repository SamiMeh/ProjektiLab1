import { useEffect, useState } from 'react';
import { Card, Col, ProgressBar, Row, Spinner } from 'react-bootstrap';
import { API, Auth, unwrapList } from '../../api/client';

function formatDate(dateStr) {
  if (!dateStr) return '—';
  return new Date(dateStr).toLocaleDateString('sq-AL', { day: 'numeric', month: 'short' });
}

export default function KandidatDashboard() {
  const user = Auth.getUser();
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({ theory: 0, practice: 0, passed: 0, totalExam: 0, debt: 0, paid: 0 });
  const [upcoming, setUpcoming] = useState([]);
  const [exams, setExams] = useState([]);
  const [theory, setTheory] = useState([]);
  const [payments, setPayments] = useState([]);

  useEffect(() => {
    async function load() {
      const id = user?.id;
      if (!id) return;
      setLoading(true);
      try {
        const q = `?kandidatId=${id}`;
        const [theoryRes, practiceRes, examsRes, paymentsRes, schedulesRes] = await Promise.allSettled([
          API.oreteTeoria.getAll(q),
          API.oretPraktike.getAll(q),
          API.provimet.getAll(q),
          API.pagesat.getAll(q),
          API.oraret.getAll(q),
        ]);

        const theoryList = unwrapList(theoryRes.status === 'fulfilled' ? theoryRes.value : []);
        const practiceList = unwrapList(practiceRes.status === 'fulfilled' ? practiceRes.value : []);
        const examsList = unwrapList(examsRes.status === 'fulfilled' ? examsRes.value : []);
        const paymentsList = unwrapList(paymentsRes.status === 'fulfilled' ? paymentsRes.value : []);
        const schedulesList = unwrapList(schedulesRes.status === 'fulfilled' ? schedulesRes.value : []);

        const theoryTarget = 32;
        const practiceTarget = 30;
        const passed = examsList.filter((e) => e.kalues === true).length;

        setStats({
          theory: theoryList.length,
          practice: practiceList.length,
          theoryPct: Math.min(100, Math.round((theoryList.length / theoryTarget) * 100)),
          practicePct: Math.min(100, Math.round((practiceList.length / practiceTarget) * 100)),
          passed,
          totalExam: examsList.length,
          examPct: examsList.length ? Math.round((passed / examsList.length) * 100) : 0,
          debt: paymentsList.filter((p) => p.statusi !== 'PAGUAR' && !p.paguar).reduce((s, p) => s + (p.shuma || 0), 0),
          paid: paymentsList.filter((p) => p.statusi === 'PAGUAR' || p.paguar).reduce((s, p) => s + (p.shuma || 0), 0),
        });

        const now = new Date();
        setUpcoming(
          schedulesList
            .filter((s) => new Date(s.data || s.dataOrarit) >= now)
            .sort((a, b) => new Date(a.data || a.dataOrarit) - new Date(b.data || b.dataOrarit))
            .slice(0, 4)
        );
        setExams(examsList.slice(0, 4));
        setTheory(theoryList.slice(0, 4));
        setPayments(paymentsList.slice(0, 4));
      } finally {
        setLoading(false);
      }
    }
    load();
  }, [user?.id]);

  if (loading) {
    return (
      <div className="page-loading">
        <Spinner animation="border" style={{ color: 'var(--brand)' }} />
      </div>
    );
  }

  return (
    <div>
      <div className="mb-4">
        <h2 className="fw-bold mb-1">Paneli im</h2>
        <p className="text-muted mb-0">
          Mirë se vini, {user?.firstName || 'Kandidat'}! Vazhdoni rrugëtimin tuaj drejt patentës.
        </p>
      </div>

      <Row className="g-3 mb-4">
        <Col md={6} xl={3}>
          <Card className="stat-card h-100">
            <Card.Body>
              <div className="text-muted small text-uppercase fw-semibold mb-2">Orë teorie</div>
              <div className="fs-2 fw-bold">{stats.theory} <small className="text-muted fs-6">/ 32</small></div>
              <ProgressBar now={stats.theoryPct} variant="primary" className="mt-2" style={{ height: 6 }} />
            </Card.Body>
          </Card>
        </Col>
        <Col md={6} xl={3}>
          <Card className="stat-card h-100">
            <Card.Body>
              <div className="text-muted small text-uppercase fw-semibold mb-2">Orë praktike</div>
              <div className="fs-2 fw-bold">{stats.practice} <small className="text-muted fs-6">/ 30</small></div>
              <ProgressBar now={stats.practicePct} variant="success" className="mt-2" style={{ height: 6 }} />
            </Card.Body>
          </Card>
        </Col>
        <Col md={6} xl={3}>
          <Card className="stat-card h-100">
            <Card.Body>
              <div className="text-muted small text-uppercase fw-semibold mb-2">Provime</div>
              <div className="fs-2 fw-bold">{stats.passed} <small className="text-muted fs-6">/ {stats.totalExam}</small></div>
              <ProgressBar now={stats.examPct} variant="info" className="mt-2" style={{ height: 6 }} />
            </Card.Body>
          </Card>
        </Col>
        <Col md={6} xl={3}>
          <Card className="stat-card h-100">
            <Card.Body>
              <div className="text-muted small text-uppercase fw-semibold mb-2">Financat</div>
              <div className="fs-2 fw-bold">{stats.debt > 0 ? `${stats.debt}€` : '✓ Rregull'}</div>
              <small className="text-muted">Paguar gjithsej: {stats.paid}€</small>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row className="g-3">
        <Col lg={6}>
          <Card className="table-card h-100">
            <Card.Header className="bg-white border-0 fw-bold">
              <i className="bi bi-calendar-week me-2 text-warning" />
              Seancat e ardhshme
            </Card.Header>
            <Card.Body className="pt-0">
              {upcoming.length ? upcoming.map((s, i) => (
                <div key={i} className="d-flex justify-content-between py-2 border-bottom">
                  <div>
                    <strong>{s.lloji === 'PRAKTIKE' ? 'Praktikë' : 'Teori'}</strong>
                    <div className="small text-muted">{formatDate(s.data || s.dataOrarit)} {s.ora || ''}</div>
                  </div>
                  <span className="small text-muted">{s.instruktori?.emri || s.instruktorEmri || 'Instruktor'}</span>
                </div>
              )) : <p className="text-muted text-center py-4 mb-0">Nuk ka seanca të planifikuara</p>}
            </Card.Body>
          </Card>
        </Col>
        <Col lg={6}>
          <Card className="table-card h-100">
            <Card.Header className="bg-white border-0 fw-bold">
              <i className="bi bi-trophy me-2 text-warning" />
              Rezultatet e provimeve
            </Card.Header>
            <Card.Body className="pt-0">
              {exams.length ? exams.map((e, i) => {
                let badge = 'warning';
                let text = 'Në pritje';
                if (e.kalues === true) { badge = 'success'; text = 'Kaloi'; }
                if (e.kalues === false) { badge = 'danger'; text = 'Nuk kaloi'; }
                return (
                  <div key={i} className="d-flex justify-content-between align-items-center py-2 border-bottom">
                    <div>
                      <strong>{e.llojiProvimit || e.lloji_provimit || 'Provim'}</strong>
                      <div className="small text-muted">{formatDate(e.dataProvimit || e.data)}</div>
                    </div>
                    <span className={`badge bg-${badge}`}>{text}</span>
                  </div>
                );
              }) : <p className="text-muted text-center py-4 mb-0">Asnjë provim</p>}
            </Card.Body>
          </Card>
        </Col>
        <Col lg={6}>
          <Card className="table-card h-100">
            <Card.Header className="bg-white border-0 fw-bold">
              <i className="bi bi-journal-bookmark me-2 text-warning" />
              Historiku i teorisë
            </Card.Header>
            <Card.Body className="pt-0">
              {theory.length ? theory.map((t, i) => (
                <div key={i} className="d-flex justify-content-between py-2 border-bottom">
                  <span>{t.tema || t.titulli || 'Leksion'}</span>
                  <span className="small text-muted">{formatDate(t.data || t.dataOres)}</span>
                </div>
              )) : <p className="text-muted text-center py-4 mb-0">Ende pa orë teorie</p>}
            </Card.Body>
          </Card>
        </Col>
        <Col lg={6}>
          <Card className="table-card h-100">
            <Card.Header className="bg-white border-0 fw-bold">
              <i className="bi bi-wallet2 me-2 text-warning" />
              Pagesat e fundit
            </Card.Header>
            <Card.Body className="pt-0">
              {payments.length ? payments.map((p, i) => (
                <div key={i} className="d-flex justify-content-between py-2 border-bottom">
                  <div>
                    <strong>{p.pershkrimi || p.lloji || 'Pagesë'}</strong>
                    <div className="small text-muted">{formatDate(p.data || p.dataPageses)}</div>
                  </div>
                  <span><b>{p.shuma || 0}€</b> {p.statusi === 'PAGUAR' || p.paguar ? '✅' : '⏳'}</span>
                </div>
              )) : <p className="text-muted text-center py-4 mb-0">Nuk ka fatura</p>}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  );
}
