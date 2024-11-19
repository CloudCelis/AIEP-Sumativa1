import dayjs from 'dayjs/esm';

export interface IPacientes {
  id: number;
  pacienteId?: number | null;
  nombre?: string | null;
  apellidoPaterno?: string | null;
  apellidoMaterno?: string | null;
  rut?: string | null;
  fechaNacimiento?: dayjs.Dayjs | null;
  genero?: string | null;
  telefono?: string | null;
  email?: string | null;
  direccion?: string | null;
}

export type NewPacientes = Omit<IPacientes, 'id'> & { id: null };
