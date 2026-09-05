import { useEffect, useState } from 'react';
import api from './axios';

const cache = new Map();

/** Fetch options from an API endpoint; `pick` extracts the label per row. */
export function useApiOptions(url, pick, fallback = []) {
  const [options, setOptions] = useState(cache.get(url) || fallback);
  useEffect(() => {
    if (!url) { setOptions(fallback); return; }
    if (cache.has(url)) { setOptions(cache.get(url)); return; }
    api.get(url)
      .then((r) => {
        const values = (r.data || []).map(pick).filter(Boolean);
        if (!values.length) return setOptions(fallback);
        cache.set(url, values);
        setOptions(values);
      })
      .catch(() => setOptions(fallback));
  }, [url]);
  return options;
}

/**
 * Fetch dropdown values from MasterTableForDrawing.
 * `fallback` is used when the column has no rows or the call fails.
 */
export function useMasterOptions(columnName, fallback = []) {
  const [options, setOptions] = useState(cache.get(columnName) || fallback);
  useEffect(() => {
    if (!columnName) { setOptions(fallback); return; }
    if (cache.has(columnName)) { setOptions(cache.get(columnName)); return; }
    api.get('/lens/queryDrawingMasterTablebyColumn', { params: { columnName } })
      .then((r) => {
        const values = r.data?.length ? r.data : fallback;
        cache.set(columnName, values);
        setOptions(values);
      })
      .catch(() => setOptions(fallback));
  }, [columnName]);
  return options;
}
