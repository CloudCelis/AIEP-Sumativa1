export interface ICentrosSalud {
  id: number;
  centroSaludID?: number | null;
  nombre?: string | null;
  direccion?: string | null;
  telefono?: string | null;
  vigente?: string | null;
}

export type NewCentrosSalud = Omit<ICentrosSalud, 'id'> & { id: null };
