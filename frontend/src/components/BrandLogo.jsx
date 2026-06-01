export default function BrandLogo({ height = 42, showText = true, subtitle, darkText = false }) {
  return (
    <div className="d-flex align-items-center gap-2">
      <img
        src="/logo.png"
        alt="AutoShkolla"
        style={{ height, width: 'auto', objectFit: 'contain' }}
      />
      {showText && (
        <div>
          <div className={`fw-bold lh-sm ${darkText ? 'text-dark' : ''}`}>AutoShkolla Pro</div>
          {subtitle && (
            <small className={darkText ? 'text-muted' : 'opacity-75'}>{subtitle}</small>
          )}
        </div>
      )}
    </div>
  );
}
