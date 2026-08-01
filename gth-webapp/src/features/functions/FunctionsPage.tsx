export function FunctionsPage() {
  return (
    <div className="flex flex-column gap-3">
      <h3 className="gth-page-title">Funciones</h3>
      <div className="surface-card border-round p-5 text-center">
        <i className="pi pi-cog" style={{ fontSize: '3rem', color: '#8b91a0' }} />
        <h4 className="mt-3 mb-1">Gestión de funciones por puesto</h4>
        <p className="text-color-secondary">
          Este módulo permitirá asignar, listar y gestionar las funciones asociadas a cada puesto de trabajo.
          El backend está en desarrollo.
        </p>
      </div>
    </div>
  );
}
