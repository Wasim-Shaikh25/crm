import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { toast } from 'sonner';
import api from '@/lib/axios';
import CrudForm from './CrudForm';
import { Skeleton } from '@/components/ui/skeleton';
import { Button } from '@/components/ui/button';
import { PDFDownloadLink } from '@react-pdf/renderer';
import DataSheetPdf from '@/pdf/DataSheetPdf';

/**
 * Load/save wrapper around CrudForm.
 * cfg: FORM_CONFIGS entry + optional updatePath, mapIn(data), mapOut(data),
 *      fetchExtra({form, setForm}) render prop.
 * idParam: route param name for edit mode.
 */
export default function EntityForm({ cfg, idParam = 'id', extra, savePath, updatePath }) {
  const params = useParams();
  const id = params[idParam];
  const navigate = useNavigate();
  const [form, setForm] = useState({});
  const [loading, setLoading] = useState(!!id);

  useEffect(() => {
    if (!id) return;
    api.get(`${cfg.base}/get`, { params: { [cfg.getParam]: id } })
      .then((r) => setForm(cfg.mapIn ? cfg.mapIn(r.data) : r.data || {}))
      .catch(() => {})
      .finally(() => setLoading(false));
  }, [id]);

  const submit = async (data) => {
    try {
      const body = cfg.mapOut ? cfg.mapOut(data) : data;
      if (id) {
        await api.put(updatePath || `${cfg.base}/update`, body);
        toast.success(`${cfg.title} updated`);
      } else {
        await api.post(savePath || `${cfg.base}/save`, body);
        toast.success(`${cfg.title} created`);
      }
      navigate(-1);
    } catch {}
  };

  if (loading) return <Skeleton className="h-96 w-full" />;
  return (
    <CrudForm
      title={`${id ? 'Edit' : 'New'} ${cfg.title}`}
      sections={cfg.sections}
      items={cfg.items}
      values={form}
      onChange={setForm}
      onSubmit={submit}
      submitLabel={id ? 'Update' : 'Save'}
      extra={
        <>
          {extra ? extra({ form, setForm }) : null}
          {id && (
            <PDFDownloadLink
              document={<DataSheetPdf title={cfg.title} sections={cfg.sections} items={cfg.items || []} data={form} />}
              fileName={`${cfg.title.replace(/\s+/g, '_')}_${form[cfg.idField] || id}.pdf`}
            >
              {({ loading: pdfLoading }) => (
                <Button type="button" variant="outline" disabled={pdfLoading}>
                  {pdfLoading ? 'Preparing PDF…' : 'Download PDF'}
                </Button>
              )}
            </PDFDownloadLink>
          )}
        </>
      }
    />
  );
}
