import api from './axios';

/** Feature flag: file uploads are temporarily disabled. Flip to true to re-enable. */
export const UPLOADS_ENABLED = false;

/** Upload a file; returns the stored filename. filetype: pumpseal|agitator|apiplan|rotaryjoint|ofm etc. */
export const uploadFile = async (file, filetype) => {
  const fd = new FormData();
  fd.append('file', file);
  fd.append('filetype', filetype);
  const { data } = await api.post(`/lens/fileUpload/file?filetype=${encodeURIComponent(filetype)}`, fd, {
    headers: { 'Content-Type': 'multipart/form-data' },
    timeout: 0,
  });
  return data;
};

/** Download a stored file by name. */
export const downloadFile = async (fileName) => {
  const res = await api.get('/lens/file/download/', {
    params: { fileName },
    responseType: 'blob',
  });
  const url = URL.createObjectURL(res.data);
  const a = Object.assign(document.createElement('a'), { href: url, download: fileName });
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
};
