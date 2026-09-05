import dayjs from 'dayjs';

/** Safe display string for any value. */
export const fmt = (v) => {
  if (v === null || v === undefined || v === '') return '-';
  if (typeof v === 'boolean') return v ? 'Yes' : 'No';
  if (typeof v === 'object') {
    if ('value' in v && 'unit' in v) return `${v.value ?? ''} ${v.unit ?? ''}`.trim() || '-';
    return '-';
  }
  return String(v);
};

export const fmtDate = (v) => {
  if (!v) return '-';
  const d = dayjs(v);
  return d.isValid() ? d.format('DD-MM-YYYY') : String(v);
};

/** camelCase -> "Camel Case" label */
export const labelize = (key) =>
  key
    .replace(/([a-z0-9])([A-Z])/g, '$1 $2')
    .replace(/[_-]+/g, ' ')
    .replace(/\b\w/g, (c) => c.toUpperCase());
