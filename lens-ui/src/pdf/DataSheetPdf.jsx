import React from 'react';
import { Document, Page, Text, View, StyleSheet } from '@react-pdf/renderer';
import { fmt, fmtDate, labelize } from '@/lib/format';

const styles = StyleSheet.create({
  page: { padding: 24, fontSize: 8, fontFamily: 'Helvetica' },
  header: { marginBottom: 10, borderBottomWidth: 1, borderBottomColor: '#0891b2', paddingBottom: 6 },
  title: { fontSize: 15, fontWeight: 'bold', color: '#0891b2' },
  sub: { fontSize: 9, color: '#475569', marginTop: 2 },
  sectionTitle: {
    fontSize: 9, fontWeight: 'bold', backgroundColor: '#f1f5f9',
    padding: '3 5', marginTop: 8, marginBottom: 2,
    borderBottomWidth: 1, borderBottomColor: '#cbd5e1',
  },
  row: { flexDirection: 'row', borderBottomWidth: 0.5, borderBottomColor: '#e2e8f0', paddingVertical: 2 },
  key: { width: '38%', fontWeight: 'bold', color: '#334155' },
  val: { width: '62%' },
});

const get = (obj, path) => path.split('.').reduce((o, k) => o?.[k], obj);
const DATE_RE = /(Date|On)$/;

/** Generic datasheet PDF: renders every configured section as label/value rows. */
export default function DataSheetPdf({ title, sections, items = [], data }) {
  return (
    <Document>
      <Page size="A4" style={styles.page}>
        <View style={styles.header}>
          <Text style={styles.title}>{title}</Text>
          <Text style={styles.sub}>Leak-Proof® Engineering Pvt. Ltd.</Text>
        </View>
        {sections.map((s) => (
          <View key={s.title}>
            <Text style={styles.sectionTitle}>{s.title}</Text>
            {s.fields.map((f) => {
              const v = get(data, f.name);
              const shown = DATE_RE.test(f.name.split('.').pop()) ? fmtDate(v) : fmt(v);
              return (
                <View style={styles.row} key={f.name}>
                  <Text style={styles.key}>{f.label || labelize(f.name.split('.').pop())} :</Text>
                  <Text style={styles.val}>{shown}</Text>
                </View>
              );
            })}
          </View>
        ))}
        {items.map(({ key, title: t, columns }) => (
          <View key={key}>
            <Text style={styles.sectionTitle}>{t || labelize(key)}</Text>
            {(data[key] || []).map((row, i) => (
              <View key={i} style={{ marginBottom: 4 }}>
                <Text style={{ fontWeight: 'bold', marginTop: 2 }}>Item {i + 1}</Text>
                {columns.map((c) => (
                  <View style={styles.row} key={c.name}>
                    <Text style={styles.key}>{c.label || labelize(c.name)} :</Text>
                    <Text style={styles.val}>{fmt(get(row, c.name))}</Text>
                  </View>
                ))}
              </View>
            ))}
            {!(data[key] || []).length && <Text style={{ color: '#94a3b8' }}>No items</Text>}
          </View>
        ))}
      </Page>
    </Document>
  );
}
