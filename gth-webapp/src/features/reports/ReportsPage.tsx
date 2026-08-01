export function ReportsPage() {
  const reports = [
    { icon: 'pi pi-users', title: 'Trabajadores', description: 'Reporte de trabajadores por dirección, departamento y situación contractual' },
    { icon: 'pi pi-file', title: 'Requerimientos DGP', description: 'Reporte de documentos de gestión de personal por estado y fecha' },
    { icon: 'pi pi-id-card', title: 'Contratos', description: 'Reporte de contratos firmados, por vencer y vencidos' },
    { icon: 'pi pi-chart-bar', title: 'Presupuesto', description: 'Resumen presupuestario por centro de costo y departamento' },
    { icon: 'pi pi-calendar', title: 'Asistencia', description: 'Control de asistencia y puntualidad del personal' },
    { icon: 'pi pi-book', title: 'Académico', description: 'Carga académica, pagos docentes y formación del personal' },
  ];

  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Reportes</h3>
      <div className="grid">
        {reports.map((r) => (
          <div key={r.title} className="col-12 md:col-6 lg:col-4">
            <div className="surface-card border-round p-4 hover:shadow-3 transition-all cursor-pointer flex align-items-center gap-3"
              style={{ borderLeft: '4px solid #4f8cff' }}>
              <i className={r.icon} style={{ fontSize: '2rem', color: '#4f8cff' }} />
              <div>
                <h4 className="m-0 mb-1">{r.title}</h4>
                <p className="m-0 text-sm text-color-secondary">{r.description}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
