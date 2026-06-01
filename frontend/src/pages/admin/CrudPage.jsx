import { useCallback, useEffect, useMemo, useState } from 'react';
import { Alert, Button, Card, Form, Modal, Spinner, Table } from 'react-bootstrap';
import { Navigate, useParams } from 'react-router-dom';
import { Auth, unwrapList } from '../../api/client';
import {
  CRUD_CONFIGS,
  LOOKUP_LOADERS,
} from '../../config/crudConfigs';
import CrudFormModal from '../../components/CrudFormModal';
import ToastStack from '../../components/ToastStack';
import { useToast } from '../../hooks/useToast';

function getRowId(row, idField) {
  return row[idField] ?? row.id;
}

export default function CrudPage({ entity: entityProp }) {
  const { entity: entityParam } = useParams();
  const entityKey = entityProp || entityParam;
  const config = CRUD_CONFIGS[entityKey];
  const { toasts, showToast, dismiss } = useToast();

  const [rows, setRows] = useState([]);
  const [lookups, setLookups] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [search, setSearch] = useState('');
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [saving, setSaving] = useState(false);
  const [deleteTarget, setDeleteTarget] = useState(null);
  const [deleting, setDeleting] = useState(false);

  const canWrite = config && (!config.adminOnly || Auth.isAdmin());

  const loadData = useCallback(async () => {
    if (!config) return;
    setLoading(true);
    setError('');
    try {
      const promises = [config.api.getAll()];
      const lookupKeys = config.lookups || [];
      lookupKeys.forEach((key) => {
        if (LOOKUP_LOADERS[key]) promises.push(LOOKUP_LOADERS[key]());
      });

      const results = await Promise.all(promises);
      setRows(unwrapList(results[0]));

      if (lookupKeys.length) {
        const next = {};
        lookupKeys.forEach((key, i) => {
          next[key] = unwrapList(results[i + 1]);
        });
        setLookups(next);
      }
    } catch (e) {
      setError(e.message);
      setRows([]);
    } finally {
      setLoading(false);
    }
  }, [config]);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const filtered = useMemo(() => {
    if (!search.trim() || !config?.searchText) return rows;
    const q = search.toLowerCase();
    return rows.filter((r) => config.searchText(r).toLowerCase().includes(q));
  }, [rows, search, config]);

  if (!config) {
    return <Alert variant="danger">Faqja nuk u gjet.</Alert>;
  }

  if (config.adminOnly && Auth.isInstruktor() && !Auth.isAdmin()) {
    return <Navigate to="/admin" replace />;
  }

  function openAdd() {
    setEditing(null);
    setModalOpen(true);
  }

  async function openEdit(row) {
    const id = getRowId(row, config.idField);
    try {
      const full = await config.api.getById(id);
      setEditing(full);
      setModalOpen(true);
    } catch (e) {
      showToast(e.message, 'error');
    }
  }

  async function handleSave(formValues) {
    setSaving(true);
    try {
      const payload = config.toPayload(formValues, lookups);
      if (editing) {
        const id = getRowId(editing, config.idField);
        await config.api.update(id, payload);
        showToast('U ndryshua me sukses!');
      } else {
        await config.api.create(payload);
        showToast('U shtua me sukses!');
      }
      setModalOpen(false);
      setEditing(null);
      loadData();
    } catch (e) {
      showToast(e.message, 'error');
    } finally {
      setSaving(false);
    }
  }

  async function confirmDelete() {
    if (!deleteTarget) return;
    setDeleting(true);
    try {
      await config.api.delete(deleteTarget);
      showToast('U fshi me sukses!');
      setDeleteTarget(null);
      loadData();
    } catch (e) {
      showToast(e.message, 'error');
    } finally {
      setDeleting(false);
    }
  }

  return (
    <div>
      <ToastStack toasts={toasts} onDismiss={dismiss} />

      <div className="d-flex flex-wrap justify-content-between align-items-start gap-3 mb-4">
        <div>
          <h2 className="fw-bold mb-1">{config.title}</h2>
          <p className="text-muted mb-0">{config.subtitle}</p>
        </div>
        {canWrite && (
          <Button
            onClick={openAdd}
            style={{ background: 'var(--brand)', borderColor: 'var(--brand)' }}
          >
            <i className="bi bi-plus-lg me-1" />
            {config.addLabel}
          </Button>
        )}
      </div>

      {error && <Alert variant="warning">{error}</Alert>}

      <Card className="table-card">
        <Card.Body className="p-3 border-bottom">
          <Form.Control
            type="search"
            placeholder="Kërko..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            style={{ maxWidth: 320 }}
          />
        </Card.Body>
        <Card.Body className="p-0">
          {loading ? (
            <div className="page-loading">
              <Spinner animation="border" style={{ color: 'var(--brand)' }} />
            </div>
          ) : (
            <Table responsive hover className="mb-0 align-middle">
              <thead className="table-light">
                <tr>
                  {config.columns.map((c) => (
                    <th key={c.key}>{c.label}</th>
                  ))}
                  {canWrite && <th style={{ width: 100 }}>Veprime</th>}
                </tr>
              </thead>
              <tbody>
                {filtered.length ? (
                  filtered.map((row) => {
                    const id = getRowId(row, config.idField);
                    return (
                      <tr key={id}>
                        {config.columns.map((col) => (
                          <td key={col.key}>
                            {col.render ? col.render(row) : row[col.key] ?? '—'}
                          </td>
                        ))}
                        {canWrite && (
                          <td>
                            <Button
                              variant="link"
                              className="text-secondary p-1"
                              title="Ndrysho"
                              onClick={() => openEdit(row)}
                            >
                              <i className="bi bi-pencil" />
                            </Button>
                            <Button
                              variant="link"
                              className="text-danger p-1"
                              title="Fshi"
                              onClick={() => setDeleteTarget(id)}
                            >
                              <i className="bi bi-trash" />
                            </Button>
                          </td>
                        )}
                      </tr>
                    );
                  })
                ) : (
                  <tr>
                    <td
                      colSpan={config.columns.length + (canWrite ? 1 : 0)}
                      className="text-center text-muted py-5"
                    >
                      Nuk ka të dhëna
                    </td>
                  </tr>
                )}
              </tbody>
            </Table>
          )}
        </Card.Body>
      </Card>

      <CrudFormModal
        show={modalOpen}
        title={editing ? `Ndrysho — ${config.title}` : config.addLabel}
        fields={config.fields}
        lookups={lookups}
        initialRecord={editing}
        fromRecord={config.fromRecord}
        onHide={() => {
          setModalOpen(false);
          setEditing(null);
        }}
        onSave={handleSave}
        saving={saving}
      />

      <Modal show={!!deleteTarget} onHide={() => setDeleteTarget(null)} centered>
        <Modal.Header closeButton>
          <Modal.Title>Jeni i sigurt?</Modal.Title>
        </Modal.Header>
        <Modal.Body>Ky veprim nuk mund të zhbëhet. Dëshironi ta fshini këtë regjistër?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setDeleteTarget(null)} disabled={deleting}>
            Anulo
          </Button>
          <Button variant="danger" onClick={confirmDelete} disabled={deleting}>
            {deleting ? 'Duke fshirë...' : 'Po, fshij'}
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
