import { Toast, ToastContainer } from 'react-bootstrap';

export default function ToastStack({ toasts, onDismiss }) {
  return (
    <ToastContainer position="bottom-end" className="p-3" style={{ zIndex: 1100 }}>
      {toasts.map((t) => (
        <Toast
          key={t.id}
          show
          onClose={() => onDismiss(t.id)}
          bg={t.type === 'error' ? 'danger' : t.type === 'info' ? 'primary' : 'success'}
          delay={3500}
          autohide
        >
          <Toast.Body className="text-white">{t.message}</Toast.Body>
        </Toast>
      ))}
    </ToastContainer>
  );
}
