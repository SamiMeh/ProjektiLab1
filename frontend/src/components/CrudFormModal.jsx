import { useEffect, useState } from 'react';
import { Button, Col, Form, Modal, Row } from 'react-bootstrap';
import { getLookupLabel, getLookupValue } from '../config/crudConfigs';

function emptyValues(fields) {
  const v = {};
  fields.forEach((f) => {
    v[f.name] = f.defaultValue ?? '';
  });
  return v;
}

export default function CrudFormModal({
  show,
  title,
  fields,
  lookups,
  initialRecord,
  fromRecord,
  onHide,
  onSave,
  saving,
}) {
  const [values, setValues] = useState({});

  useEffect(() => {
    if (!show) return;
    if (initialRecord && fromRecord) {
      setValues(fromRecord(initialRecord));
    } else {
      setValues(emptyValues(fields));
    }
  }, [show, initialRecord, fields, fromRecord]);

  function set(name, val) {
    setValues((prev) => ({ ...prev, [name]: val }));
  }

  function handleSubmit(e) {
    e.preventDefault();
    onSave(values);
  }

  function renderField(f) {
    const col = f.colSpan || 6;

    if (f.type === 'select') {
      let options = f.options || [];
      if (f.lookup && lookups[f.lookup]) {
        options = lookups[f.lookup].map((item) => ({
          value: String(getLookupValue(f.lookup, item)),
          label: getLookupLabel(f.lookup, item),
        }));
      }
      return (
        <Col md={col} key={f.name}>
          <Form.Group className="mb-3">
            <Form.Label>{f.label}</Form.Label>
            <Form.Select
              value={values[f.name] ?? ''}
              onChange={(e) => set(f.name, e.target.value)}
              required={f.required}
            >
              <option value="">-- Zgjidh --</option>
              {options.map((o) => (
                <option key={o.value} value={o.value}>
                  {o.label}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
        </Col>
      );
    }

    if (f.type === 'textarea') {
      return (
        <Col md={col} key={f.name}>
          <Form.Group className="mb-3">
            <Form.Label>{f.label}</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              value={values[f.name] ?? ''}
              onChange={(e) => set(f.name, e.target.value)}
              required={f.required}
            />
          </Form.Group>
        </Col>
      );
    }

    return (
      <Col md={col} key={f.name}>
        <Form.Group className="mb-3">
          <Form.Label>{f.label}</Form.Label>
          <Form.Control
            type={f.type || 'text'}
            value={values[f.name] ?? ''}
            onChange={(e) => set(f.name, e.target.value)}
            required={f.required}
            min={f.min}
            max={f.max}
            step={f.step}
          />
        </Form.Group>
      </Col>
    );
  }

  return (
    <Modal show={show} onHide={onHide} centered size="lg">
      <Form onSubmit={handleSubmit}>
        <Modal.Header closeButton>
          <Modal.Title>{title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row>{fields.map(renderField)}</Row>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={onHide} disabled={saving}>
            Anulo
          </Button>
          <Button
            type="submit"
            disabled={saving}
            style={{ background: 'var(--brand)', borderColor: 'var(--brand)' }}
          >
            {saving ? 'Duke ruajtur...' : 'Ruaj'}
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
}
