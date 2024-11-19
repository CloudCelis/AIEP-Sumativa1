import { ICentrosSalud } from 'app/entities/centros-salud/centros-salud.model';

export interface IMedico {
  id: number;
  medicoId?: number | null;
  nombre?: string | null;
  apellidoPaterno?: string | null;
  apellidoMaterno?: string | null;
  especialidad?: string | null;
  telefono?: string | null;
  correo?: string | null;
  centroSaludId?: number | null;
  centrosSalud?: ICentrosSalud | null;
}

export type NewMedico = Omit<IMedico, 'id'> & { id: null };
