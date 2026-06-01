import { useEffect, useMemo, useState } from 'react';
import { Card, Spinner, Table } from 'react-bootstrap';
import { useLocation } from 'react-router-dom';
import { API, Auth, unwrapList } from '../../api/client';

const PAGE_META = {
  oraret: {
    title: 'Oraret e Mia',
    subtitle: 'Orari juaj i planifikuar',
    columns: [
      { key: 'ditaJaves', label: 'Dita' },
      { key: 'oraFillimit', label: 'Fillon' },
      { key: 'oraMbarimit', label: 'Mbaron' },
      { key: 'lloji', label: 'Lloji' },
    ],
    fetch: () => API.oraret.getAll(),
  },
  'ore-teorie': {
    title: 'Orët e Teorisë',
    subtitle: 'Leksionet tuaja teorike',
    columns: [
      { key: 'tema', label: 'Tema', render: (r) => r.tema || r.titulli || '—' },
      { key: 'dataOres', label: 'Data', render: (r) => r.dataOres || r.data || '—' },
      { key: 'oraFillimit', label: 'Ora' },
    ],
    fetch: (id) => API.oreteTeoria.getAll(`?kandidatId=${id}`),
  },
  'ore-praktike': {
    title: 'Orët e Praktikës',
    subtitle: 'Seancat tuaja praktike',
    columns: [
      { key: 'dataOres', label: 'Data' },
      { key: 'oraFillimit', label: 'Fillon' },
      { key: 'oraMbarimit', label: 'Mbaron' },
      {
        key: 'instruktori',
        label: 'Instruktori',
        render: (r) => `${r.instruktori?.emri || ''} ${r.instruktori?.mbiemri || ''}`.trim() || '—',
      },
    ],
    fetch: (id) => API.oretPraktike.getAll(`?kandidatId=${id}`),
  },
  provimet: {
    title: 'Provimet e Mia',
    subtitle: 'Rezultatet e provimeve',
    columns: [
      { key: 'llojiProvimit', label: 'Lloji' },
      { key: 'dataProvimit', label: 'Data' },
      { key: 'piket', label: 'Pikët' },
      {
        key: 'kalues',
        label: 'Rezultati',
        render: (r) => (r.kalues === true ? 'Kaloi' : r.kalues === false ? 'Nuk kaloi' : 'Në pritje'),
      },
    ],
    fetch: (id) => API.provimet.getAll(`?kandidatId=${id}`),
  },
  pagesat: {
    title: 'Pagesat e Mia',
    subtitle: 'Historiku i pagesave',
    columns: [
      { key: 'pershkrimi', label: 'Përshkrimi', render: (r) => r.pershkrimi || r.lloji || 'Pagesë' },
      { key: 'shuma', label: 'Shuma', render: (r) => `${r.shuma || 0}€` },
      {
        key: 'statusi',
        label: 'Statusi',
        render: (r) => (r.statusi === 'PAGUAR' || r.paguar ? 'Paguar' : 'Papaguar'),
      },
    ],
    fetch: (id) => API.pagesat.getAll(`?kandidatId=${id}`),
  },
};

export default function KandidatListPage({ type }) {
  const location = useLocation();
  const resolvedType = type || location.pathname.split('/').pop();
  const meta = PAGE_META[resolvedType];
  const user = Auth.getUser();
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!meta) return;
    async function load() {
      setLoading(true);
      setError('');
      try {
        const res = await meta.fetch(user?.id);
        setRows(unwrapList(res));
      } catch (e) {
        setError(e.message);
        setRows([]);
      } finally {
        setLoading(false);
      }
    }
    load();
  }, [resolvedType, user?.id, meta]);

  const tableRows = useMemo(() => {
    if (!meta) return null;
    return rows.map((row, idx) => (
      <tr key={row.id || row.orarId || row.provimId || row.orePraktikeId || idx}>
        {meta.columns.map((col) => (
          <td key={col.key}>{col.render ? col.render(row) : row[col.key] ?? '—'}</td>
        ))}
      </tr>
    ));
  }, [rows, meta]);

  if (!meta) {
    return <p className="text-danger">Faqja nuk u gjet.</p>;
  }

  return (
    <div>
      <div className="mb-4">
        <h2 className="fw-bold mb-1">{meta.title}</h2>
        <p className="text-muted mb-0">{meta.subtitle}</p>
      </div>

      {error && <div className="alert alert-warning">{error}</div>}

      <Card className="table-card">
        <Card.Body className="p-0">
          {loading ? (
            <div className="page-loading">
              <Spinner animation="border" style={{ color: 'var(--brand)' }} />
            </div>
          ) : (
            <Table responsive hover className="mb-0 align-middle">
              <thead className="table-light">
                <tr>
                  {meta.columns.map((c) => (
                    <th key={c.key}>{c.label}</th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {tableRows?.length ? tableRows : (
                  <tr>
                    <td colSpan={meta.columns.length} className="text-center text-muted py-5">
                      Nuk ka të dhëna
                    </td>
                  </tr>
                )}
              </tbody>
            </Table>
          )}
        </Card.Body>
      </Card>
    </div>
  );
}
