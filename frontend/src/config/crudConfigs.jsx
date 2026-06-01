import { API } from '../api/client';

const DITET = ['E Hënë', 'E Martë', 'E Mërkurë', 'E Enjte', 'E Premte', 'E Shtunë', 'E Diel'];
const STATUS_REG = [
  { value: 'AKTIV', label: 'Aktiv' },
  { value: 'PËRFUNDUAR', label: 'Përfunduar' },
  { value: 'ANULUAR', label: 'Anuluar' },
];
const STATUS_PAGESA = [
  { value: 'PAGUAR', label: 'Paguar' },
  { value: 'PAPAGUAR', label: 'Papaguar' },
  { value: 'PJESËRISHT', label: 'Pjesërisht' },
];
const LLOJI_ORAR = [
  { value: 'TEORI', label: 'Teori' },
  { value: 'PRAKTIKË', label: 'Praktikë' },
  { value: 'TË DY', label: 'Të dy' },
];
const LLOJI_PROVIM = [
  { value: 'TEORI', label: 'Teori' },
  { value: 'PRAKTIKË', label: 'Praktikë' },
];

const today = () => new Date().toISOString().split('T')[0];
const toTime = (t) => (t && t.length === 5 ? `${t}:00` : t || null);
const fmtTime = (t) => (t ? String(t).substring(0, 5) : '');

function statusBadge(status) {
  const map = {
    AKTIV: 'success',
    PËRFUNDUAR: 'primary',
    ANULUAR: 'danger',
    PAGUAR: 'success',
    PAPAGUAR: 'danger',
    PJESËRISHT: 'warning',
  };
  return map[status] || 'secondary';
}

export const CRUD_CONFIGS = {
  kandidatet: {
    title: 'Kandidatët',
    subtitle: 'Menaxhimi i kandidatëve të regjistruar',
    adminOnly: true,
    addLabel: 'Kandidat i ri',
    idField: 'kandidatId',
    api: API.kandidatet,
    searchText: (r) => `${r.emri} ${r.mbiemri} ${r.email} ${r.numriPersonal}`,
    columns: [
      { key: 'kandidatId', label: 'ID' },
      { key: 'emri', label: 'Emri', render: (r) => <strong>{`${r.emri || ''} ${r.mbiemri || ''}`}</strong> },
      { key: 'numriPersonal', label: 'Nr. Personal' },
      { key: 'email', label: 'Email' },
      { key: 'telefoni', label: 'Telefoni' },
      { key: 'dataRegjistrimit', label: 'Data Regj.' },
    ],
    fields: [
      { name: 'emri', label: 'Emri', type: 'text', required: true },
      { name: 'mbiemri', label: 'Mbiemri', type: 'text', required: true },
      { name: 'numriPersonal', label: 'Numri Personal', type: 'text', required: true },
      { name: 'dataLindjes', label: 'Datëlindja', type: 'date', required: true },
      { name: 'email', label: 'Email', type: 'email', required: true },
      { name: 'telefoni', label: 'Telefoni', type: 'tel' },
      { name: 'adresa', label: 'Adresa', type: 'text', required: false },
      { name: 'dataRegjistrimit', label: 'Data Regjistrimit', type: 'date', defaultValue: today },
    ],
    toPayload: (f) => ({ ...f }),
    fromRecord: (r) => ({ ...r }),
  },

  kategorite: {
    title: 'Kategoritë e Patentës',
    subtitle: 'Menaxhimi i kategorive dhe çmimeve',
    adminOnly: true,
    addLabel: 'Kategori e re',
    idField: 'kategoriId',
    api: API.kategorite,
    searchText: (r) => `${r.emriKategorise} ${r.pershkrimi}`,
    columns: [
      { key: 'kategoriId', label: 'ID' },
      { key: 'emriKategorise', label: 'Emri', render: (r) => <strong>{r.emriKategorise}</strong> },
      { key: 'moshaMinimale', label: 'Mosha Min.' },
      { key: 'oreTeori', label: 'Orë Teorie' },
      { key: 'orePraktike', label: 'Orë Praktike' },
      { key: 'cmimi', label: 'Çmimi', render: (r) => <strong>€{r.cmimi || 0}</strong> },
    ],
    fields: [
      { name: 'emriKategorise', label: 'Emri i kategorisë', type: 'text', required: true },
      { name: 'moshaMinimale', label: 'Mosha minimale', type: 'number', min: 16, defaultValue: 18 },
      { name: 'oreTeori', label: 'Orë teorie', type: 'number', min: 0, defaultValue: 30 },
      { name: 'orePraktike', label: 'Orë praktike', type: 'number', min: 0, defaultValue: 20 },
      { name: 'cmimi', label: 'Çmimi (€)', type: 'number', step: '0.01', min: 0, defaultValue: 0 },
      { name: 'pershkrimi', label: 'Përshkrimi', type: 'textarea', required: false, colSpan: 12 },
    ],
    toPayload: (f) => ({
      ...f,
      moshaMinimale: Number(f.moshaMinimale),
      oreTeori: Number(f.oreTeori),
      orePraktike: Number(f.orePraktike),
      cmimi: parseFloat(f.cmimi),
    }),
    fromRecord: (r) => ({ ...r }),
  },

  regjistrime: {
    title: 'Regjistrimet',
    subtitle: 'Menaxhimi i regjistrimeve në kurse',
    adminOnly: true,
    addLabel: 'Regjistrim i ri',
    idField: 'regjistrimId',
    api: API.regjistrime,
    lookups: ['kandidatet', 'kategorite'],
    searchText: (r) => `${r.regjistrimId} ${r.statusi}`,
    columns: [
      { key: 'regjistrimId', label: 'ID' },
      {
        key: 'kandidati',
        label: 'Kandidati',
        render: (r) => (r.kandidati ? <strong>{`${r.kandidati.emri} ${r.kandidati.mbiemri}`}</strong> : '—'),
      },
      {
        key: 'kategoriPatente',
        label: 'Kategoria',
        render: (r) => r.kategoriPatente?.emriKategorise || '—',
      },
      { key: 'dataRegjistrimit', label: 'Data' },
      { key: 'cimiTotal', label: 'Çmimi', render: (r) => `€${r.cimiTotal || 0}` },
      { key: 'shumaPaguar', label: 'Paguar', render: (r) => `€${r.shumaPaguar || 0}` },
      {
        key: 'statusi',
        label: 'Statusi',
        render: (r) => (
          <span className={`badge bg-${statusBadge(r.statusi)}`}>{r.statusi || 'AKTIV'}</span>
        ),
      },
    ],
    fields: [
      { name: 'kandidatId', label: 'Kandidati', type: 'select', lookup: 'kandidatet', required: true },
      { name: 'kategoriId', label: 'Kategoria', type: 'select', lookup: 'kategorite', required: true },
      { name: 'dataRegjistrimit', label: 'Data regjistrimit', type: 'date', defaultValue: today },
      { name: 'statusi', label: 'Statusi', type: 'select', options: STATUS_REG, defaultValue: 'AKTIV' },
      { name: 'cimiTotal', label: 'Çmimi total (€)', type: 'number', step: '0.01', min: 0, defaultValue: 0 },
      { name: 'shumaPaguar', label: 'Shuma e paguar (€)', type: 'number', step: '0.01', min: 0, defaultValue: 0 },
    ],
    toPayload: (f) => ({
      kandidati: { kandidatId: parseInt(f.kandidatId, 10) },
      kategoriPatente: { kategoriId: parseInt(f.kategoriId, 10) },
      dataRegjistrimit: f.dataRegjistrimit,
      statusi: f.statusi,
      cimiTotal: parseFloat(f.cimiTotal),
      shumaPaguar: parseFloat(f.shumaPaguar),
    }),
    fromRecord: (r) => ({
      kandidatId: r.kandidati?.kandidatId || r.kandidatId || '',
      kategoriId: r.kategoriPatente?.kategoriId || r.kategoriId || '',
      dataRegjistrimit: r.dataRegjistrimit || today(),
      statusi: r.statusi || 'AKTIV',
      cimiTotal: r.cimiTotal ?? 0,
      shumaPaguar: r.shumaPaguar ?? 0,
    }),
  },

  instruktoret: {
    title: 'Instruktorët',
    subtitle: 'Menaxhimi i instruktorëve të autoshkollës',
    adminOnly: true,
    addLabel: 'Instruktor i ri',
    idField: 'instruktorId',
    api: API.instruktoret,
    searchText: (r) => `${r.emri} ${r.mbiemri} ${r.email}`,
    columns: [
      { key: 'instruktorId', label: 'ID' },
      { key: 'emri', label: 'Emri', render: (r) => <strong>{`${r.emri || ''} ${r.mbiemri || ''}`}</strong> },
      { key: 'email', label: 'Email' },
      { key: 'telefoni', label: 'Telefoni' },
      { key: 'licencaNr', label: 'Nr. Licencës' },
      { key: 'kategoriteLeJuara', label: 'Kategoritë' },
      {
        key: 'aktiv',
        label: 'Statusi',
        render: (r) => (
          <span className={`badge bg-${r.aktiv !== false ? 'success' : 'danger'}`}>
            {r.aktiv !== false ? 'AKTIV' : 'ANULUAR'}
          </span>
        ),
      },
    ],
    fields: [
      { name: 'emri', label: 'Emri', type: 'text', required: true },
      { name: 'mbiemri', label: 'Mbiemri', type: 'text', required: true },
      { name: 'email', label: 'Email', type: 'email', required: true },
      { name: 'telefoni', label: 'Telefoni', type: 'tel' },
      { name: 'licencaNr', label: 'Nr. Licencës', type: 'text' },
      { name: 'kategoriteLeJuara', label: 'Kategoritë e lejuara', type: 'text', required: false },
      {
        name: 'aktiv',
        label: 'Aktiv',
        type: 'select',
        options: [
          { value: 'true', label: 'Po' },
          { value: 'false', label: 'Jo' },
        ],
        defaultValue: 'true',
      },
    ],
    toPayload: (f) => ({ ...f, aktiv: f.aktiv === 'true' || f.aktiv === true }),
    fromRecord: (r) => ({
      ...r,
      aktiv: r.aktiv !== false ? 'true' : 'false',
    }),
  },

  automjetet: {
    title: 'Automjetet',
    subtitle: 'Menaxhimi i flotës së automjeteve',
    adminOnly: true,
    addLabel: 'Automjet i ri',
    idField: 'automjetId',
    api: API.automjetet,
    searchText: (r) => `${r.marka} ${r.modeli} ${r.targa}`,
    columns: [
      { key: 'automjetId', label: 'ID' },
      { key: 'marka', label: 'Marka/Modeli', render: (r) => <strong>{`${r.marka || ''} ${r.modeli || ''}`}</strong> },
      { key: 'targa', label: 'Targa' },
      { key: 'vitiProdhimit', label: 'Viti' },
      { key: 'kategoriaPatentes', label: 'Kategoria' },
      { key: 'dataRegjistrimitTeknik', label: 'Regj. Teknik' },
      {
        key: 'aktiv',
        label: 'Statusi',
        render: (r) => (
          <span className={`badge bg-${r.aktiv !== false ? 'success' : 'danger'}`}>
            {r.aktiv !== false ? 'AKTIV' : 'ANULUAR'}
          </span>
        ),
      },
    ],
    fields: [
      { name: 'marka', label: 'Marka', type: 'text', required: true },
      { name: 'modeli', label: 'Modeli', type: 'text', required: true },
      { name: 'targa', label: 'Targa', type: 'text', required: true },
      {
        name: 'vitiProdhimit',
        label: 'Viti i prodhimit',
        type: 'number',
        min: 1990,
        max: 2030,
        defaultValue: new Date().getFullYear(),
      },
      { name: 'kategoriaPatentes', label: 'Kategoria e patentës', type: 'text' },
      { name: 'dataRegjistrimitTeknik', label: 'Data Regj. Teknik', type: 'date' },
      {
        name: 'aktiv',
        label: 'Aktiv',
        type: 'select',
        options: [
          { value: 'true', label: 'Po' },
          { value: 'false', label: 'Jo' },
        ],
        defaultValue: 'true',
      },
    ],
    toPayload: (f) => ({
      ...f,
      vitiProdhimit: Number(f.vitiProdhimit),
      aktiv: f.aktiv === 'true' || f.aktiv === true,
    }),
    fromRecord: (r) => ({
      ...r,
      aktiv: r.aktiv !== false ? 'true' : 'false',
    }),
  },

  pagesat: {
    title: 'Pagesat',
    subtitle: 'Menaxhimi i pagesave dhe faturave',
    adminOnly: true,
    addLabel: 'Pagesë e re',
    idField: 'pageseId',
    api: API.pagesat,
    lookups: ['regjistrime'],
    searchText: (r) => `${r.pershkrimi} ${r.statusi} ${r.metoda}`,
    columns: [
      { key: 'pageseId', label: 'ID' },
      {
        key: 'regjistrimi',
        label: 'Regjistrim',
        render: (r) => `Regjistrim #${r.regjistrimi?.regjistrimId || '—'}`,
      },
      { key: 'shuma', label: 'Shuma', render: (r) => <strong>€{r.shuma || 0}</strong> },
      { key: 'dataPageses', label: 'Data' },
      { key: 'metoda', label: 'Metoda' },
      { key: 'pershkrimi', label: 'Përshkrimi' },
      {
        key: 'statusi',
        label: 'Statusi',
        render: (r) => <span className={`badge bg-${statusBadge(r.statusi)}`}>{r.statusi}</span>,
      },
    ],
    fields: [
      { name: 'regjistrimId', label: 'Regjistrimi', type: 'select', lookup: 'regjistrime', required: true },
      { name: 'shuma', label: 'Shuma (€)', type: 'number', step: '0.01', min: 0, required: true },
      { name: 'dataPageses', label: 'Data pagesës', type: 'date', defaultValue: today },
      { name: 'metoda', label: 'Metoda', type: 'text', defaultValue: 'CASH' },
      { name: 'pershkrimi', label: 'Përshkrimi', type: 'text', required: false },
      { name: 'statusi', label: 'Statusi', type: 'select', options: STATUS_PAGESA, defaultValue: 'PAPAGUAR' },
    ],
    toPayload: (f) => ({
      regjistrimi: { regjistrimId: parseInt(f.regjistrimId, 10) },
      shuma: parseFloat(f.shuma),
      dataPageses: f.dataPageses,
      metoda: f.metoda,
      pershkrimi: f.pershkrimi,
      statusi: f.statusi,
    }),
    fromRecord: (r) => ({
      regjistrimId: r.regjistrimi?.regjistrimId || r.regjistrimId || '',
      shuma: r.shuma ?? '',
      dataPageses: r.dataPageses || today(),
      metoda: r.metoda || 'CASH',
      pershkrimi: r.pershkrimi || '',
      statusi: r.statusi || 'PAPAGUAR',
    }),
  },

  'ore-teorie': {
    title: 'Orët e Teorisë',
    subtitle: 'Planifikimi i orëve teorike',
    adminOnly: false,
    addLabel: 'Orë e re',
    idField: 'oreTeoriId',
    api: API.oreteTeoria,
    lookups: ['regjistrime', 'instruktoret'],
    searchText: (r) => `${r.tema} ${r.dataOres}`,
    columns: [
      { key: 'oreTeoriId', label: 'ID' },
      {
        key: 'regjistrimi',
        label: 'Regjistrim',
        render: (r) => `#${r.regjistrimi?.regjistrimId || '—'}`,
      },
      {
        key: 'instruktori',
        label: 'Instruktori',
        render: (r) => `${r.instruktori?.emri || ''} ${r.instruktori?.mbiemri || ''}`.trim() || '—',
      },
      { key: 'dataOres', label: 'Data' },
      { key: 'oraFillimit', label: 'Fillon', render: (r) => fmtTime(r.oraFillimit) },
      { key: 'oraMbarimit', label: 'Mbaron', render: (r) => fmtTime(r.oraMbarimit) },
      { key: 'tema', label: 'Tema' },
      {
        key: 'prezent',
        label: 'Prezent',
        render: (r) => (r.prezent ? '✓ Po' : 'Jo'),
      },
    ],
    fields: [
      { name: 'regjistrimId', label: 'Regjistrimi', type: 'select', lookup: 'regjistrime', required: true },
      { name: 'instruktorId', label: 'Instruktori', type: 'select', lookup: 'instruktoret', required: true },
      { name: 'dataOres', label: 'Data', type: 'date', defaultValue: today },
      { name: 'oraFillimit', label: 'Ora fillimit', type: 'time', required: true },
      { name: 'oraMbarimit', label: 'Ora mbarimit', type: 'time', required: true },
      { name: 'tema', label: 'Tema', type: 'text', required: true },
      {
        name: 'prezent',
        label: 'Prezent',
        type: 'select',
        options: [
          { value: 'true', label: 'Po' },
          { value: 'false', label: 'Jo' },
        ],
        defaultValue: 'false',
      },
    ],
    toPayload: (f) => ({
      regjistrimi: { regjistrimId: parseInt(f.regjistrimId, 10) },
      instruktori: { instruktorId: parseInt(f.instruktorId, 10) },
      dataOres: f.dataOres,
      oraFillimit: toTime(f.oraFillimit),
      oraMbarimit: toTime(f.oraMbarimit),
      tema: f.tema,
      prezent: f.prezent === 'true' || f.prezent === true,
    }),
    fromRecord: (r) => ({
      regjistrimId: r.regjistrimi?.regjistrimId || r.regjistrimId || '',
      instruktorId: r.instruktori?.instruktorId || r.instruktorId || '',
      dataOres: r.dataOres || today(),
      oraFillimit: fmtTime(r.oraFillimit),
      oraMbarimit: fmtTime(r.oraMbarimit),
      tema: r.tema || '',
      prezent: r.prezent ? 'true' : 'false',
    }),
  },

  'ore-praktike': {
    title: 'Orët e Praktikës',
    subtitle: 'Planifikimi i orëve praktike të vozitjes',
    adminOnly: false,
    addLabel: 'Orë e re',
    idField: 'orePraktikeId',
    api: API.oretPraktike,
    lookups: ['regjistrime', 'instruktoret', 'automjetet'],
    searchText: (r) => `${r.dataOres}`,
    columns: [
      { key: 'orePraktikeId', label: 'ID' },
      {
        key: 'regjistrimi',
        label: 'Regjistrim',
        render: (r) => `#${r.regjistrimi?.regjistrimId || '—'}`,
      },
      {
        key: 'instruktori',
        label: 'Instruktori',
        render: (r) => `${r.instruktori?.emri || ''} ${r.instruktori?.mbiemri || ''}`.trim() || '—',
      },
      {
        key: 'automjeti',
        label: 'Automjeti',
        render: (r) =>
          r.automjeti
            ? `${r.automjeti.marka} ${r.automjeti.modeli} (${r.automjeti.targa})`
            : '—',
      },
      { key: 'dataOres', label: 'Data' },
      { key: 'oraFillimit', label: 'Fillon', render: (r) => fmtTime(r.oraFillimit) },
      { key: 'oraMbarimit', label: 'Mbaron', render: (r) => fmtTime(r.oraMbarimit) },
      { key: 'vleresimi', label: 'Vlerësimi' },
    ],
    fields: [
      { name: 'regjistrimId', label: 'Regjistrimi', type: 'select', lookup: 'regjistrime', required: true },
      { name: 'instruktorId', label: 'Instruktori', type: 'select', lookup: 'instruktoret', required: true },
      { name: 'automjetId', label: 'Automjeti', type: 'select', lookup: 'automjetet', required: true },
      { name: 'dataOres', label: 'Data', type: 'date', defaultValue: today },
      { name: 'oraFillimit', label: 'Ora fillimit', type: 'time', required: true },
      { name: 'oraMbarimit', label: 'Ora mbarimit', type: 'time', required: true },
      { name: 'vleresimi', label: 'Vlerësimi (1-10)', type: 'number', min: 1, max: 10, required: false },
    ],
    toPayload: (f) => ({
      regjistrimi: { regjistrimId: parseInt(f.regjistrimId, 10) },
      instruktori: { instruktorId: parseInt(f.instruktorId, 10) },
      automjeti: { automjetId: parseInt(f.automjetId, 10) },
      dataOres: f.dataOres,
      oraFillimit: toTime(f.oraFillimit),
      oraMbarimit: toTime(f.oraMbarimit),
      vleresimi: f.vleresimi ? Number(f.vleresimi) : null,
    }),
    fromRecord: (r) => ({
      regjistrimId: r.regjistrimi?.regjistrimId || r.regjistrimId || '',
      instruktorId: r.instruktori?.instruktorId || r.instruktorId || '',
      automjetId: r.automjeti?.automjetId || r.automjetId || '',
      dataOres: r.dataOres || today(),
      oraFillimit: fmtTime(r.oraFillimit),
      oraMbarimit: fmtTime(r.oraMbarimit),
      vleresimi: r.vleresimi ?? '',
    }),
  },

  provimet: {
    title: 'Provimet',
    subtitle: 'Regjistrimi i rezultateve të provimeve',
    adminOnly: false,
    addLabel: 'Provim i ri',
    idField: 'provimId',
    api: API.provimet,
    lookups: ['regjistrime'],
    searchText: (r) => `${r.llojiProvimit} ${r.shenimet}`,
    columns: [
      { key: 'provimId', label: 'ID' },
      {
        key: 'regjistrimi',
        label: 'Regjistrim',
        render: (r) => `#${r.regjistrimi?.regjistrimId || '—'}`,
      },
      { key: 'llojiProvimit', label: 'Lloji' },
      { key: 'dataProvimit', label: 'Data' },
      { key: 'piket', label: 'Pikët', render: (r) => <strong>{r.piket ?? 0}</strong> },
      {
        key: 'kalues',
        label: 'Rezultati',
        render: (r) => {
          if (r.kalues === true) return <span className="badge bg-success">Kaloi</span>;
          if (r.kalues === false) return <span className="badge bg-danger">Nuk kaloi</span>;
          return <span className="badge bg-warning text-dark">Në pritje</span>;
        },
      },
      { key: 'shenimet', label: 'Shënime' },
    ],
    fields: [
      { name: 'regjistrimId', label: 'Regjistrimi', type: 'select', lookup: 'regjistrime', required: true },
      { name: 'llojiProvimit', label: 'Lloji i provimit', type: 'select', options: LLOJI_PROVIM, required: true },
      { name: 'dataProvimit', label: 'Data e provimit', type: 'date', defaultValue: today },
      { name: 'piket', label: 'Pikët', type: 'number', min: 0, max: 100, defaultValue: 0 },
      {
        name: 'kalues',
        label: 'Kaloi',
        type: 'select',
        options: [
          { value: '', label: 'Në pritje' },
          { value: 'true', label: 'Po' },
          { value: 'false', label: 'Jo' },
        ],
        defaultValue: '',
      },
      { name: 'shenimet', label: 'Shënime', type: 'textarea', required: false, colSpan: 12 },
    ],
    toPayload: (f) => ({
      regjistrimi: { regjistrimId: parseInt(f.regjistrimId, 10) },
      llojiProvimit: f.llojiProvimit,
      dataProvimit: f.dataProvimit,
      piket: Number(f.piket) || 0,
      kalues: f.kalues === '' ? null : f.kalues === 'true',
      shenimet: f.shenimet || '',
    }),
    fromRecord: (r) => ({
      regjistrimId: r.regjistrimi?.regjistrimId || r.regjistrimId || '',
      llojiProvimit: r.llojiProvimit || '',
      dataProvimit: r.dataProvimit || today(),
      piket: r.piket ?? 0,
      kalues: r.kalues === true ? 'true' : r.kalues === false ? 'false' : '',
      shenimet: r.shenimet || '',
    }),
  },

  oraret: {
    title: 'Oraret',
    subtitle: 'Krijimi i orareve të instruktorëve',
    adminOnly: false,
    addLabel: 'Orar i ri',
    idField: 'orarId',
    api: API.oraret,
    lookups: ['instruktoret'],
    searchText: (r) => `${r.ditaJaves} ${r.lloji}`,
    columns: [
      { key: 'orarId', label: 'ID' },
      {
        key: 'instruktori',
        label: 'Instruktori',
        render: (r) =>
          r.instruktori ? (
            <strong>{`${r.instruktori.emri} ${r.instruktori.mbiemri}`}</strong>
          ) : (
            '—'
          ),
      },
      { key: 'ditaJaves', label: 'Dita' },
      { key: 'oraFillimit', label: 'Fillon', render: (r) => fmtTime(r.oraFillimit) },
      { key: 'oraMbarimit', label: 'Mbaron', render: (r) => fmtTime(r.oraMbarimit) },
      { key: 'lloji', label: 'Lloji' },
      {
        key: 'aktiv',
        label: 'Aktiv',
        render: (r) => (
          <span className={`badge bg-${r.aktiv !== false ? 'success' : 'danger'}`}>
            {r.aktiv !== false ? 'AKTIV' : 'ANULUAR'}
          </span>
        ),
      },
    ],
    fields: [
      { name: 'instruktorId', label: 'Instruktori', type: 'select', lookup: 'instruktoret', required: true },
      {
        name: 'ditaJaves',
        label: 'Dita e javës',
        type: 'select',
        options: DITET.map((d) => ({ value: d, label: d })),
        required: true,
      },
      { name: 'oraFillimit', label: 'Ora fillimit', type: 'time', required: true },
      { name: 'oraMbarimit', label: 'Ora mbarimit', type: 'time', required: true },
      { name: 'lloji', label: 'Lloji', type: 'select', options: LLOJI_ORAR, required: true },
      {
        name: 'aktiv',
        label: 'Aktiv',
        type: 'select',
        options: [
          { value: 'true', label: 'Po' },
          { value: 'false', label: 'Jo' },
        ],
        defaultValue: 'true',
      },
    ],
    toPayload: (f) => ({
      instruktori: { instruktorId: parseInt(f.instruktorId, 10) },
      ditaJaves: f.ditaJaves,
      oraFillimit: toTime(f.oraFillimit),
      oraMbarimit: toTime(f.oraMbarimit),
      lloji: f.lloji,
      aktiv: f.aktiv === 'true' || f.aktiv === true,
    }),
    fromRecord: (r) => ({
      instruktorId: r.instruktori?.instruktorId || r.instruktorId || '',
      ditaJaves: r.ditaJaves || '',
      oraFillimit: fmtTime(r.oraFillimit),
      oraMbarimit: fmtTime(r.oraMbarimit),
      lloji: r.lloji || '',
      aktiv: r.aktiv !== false ? 'true' : 'false',
    }),
  },
};

export const LOOKUP_LOADERS = {
  kandidatet: () => API.kandidatet.getAll(),
  kategorite: () => API.kategorite.getAll(),
  regjistrime: () => API.regjistrime.getAll(),
  instruktoret: () => API.instruktoret.getAll(),
  automjetet: () => API.automjetet.getAll(),
};

export function getLookupLabel(lookupKey, item) {
  switch (lookupKey) {
    case 'kandidatet':
      return `${item.emri} ${item.mbiemri}`;
    case 'kategorite':
      return item.emriKategorise;
    case 'regjistrime': {
      const k = item.kandidati;
      const name = k ? `${k.emri} ${k.mbiemri}` : '';
      return `Regjistrim #${item.regjistrimId}${name ? ` — ${name}` : ''}`;
    }
    case 'instruktoret':
      return `${item.emri} ${item.mbiemri}`;
    case 'automjetet':
      return `${item.marka} ${item.modeli} (${item.targa})`;
    default:
      return String(item.id || '');
  }
}

export function getLookupValue(lookupKey, item) {
  const map = {
    kandidatet: 'kandidatId',
    kategorite: 'kategoriId',
    regjistrime: 'regjistrimId',
    instruktoret: 'instruktorId',
    automjetet: 'automjetId',
  };
  const key = map[lookupKey];
  return item[key] || item.id;
}
