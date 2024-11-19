import dayjs from 'dayjs/esm';
import { IPacientes } from 'app/entities/pacientes/pacientes.model';
import { IMedico } from 'app/entities/medico/medico.model';
import { ICentrosSalud } from 'app/entities/centros-salud/centros-salud.model';

export interface IReserva {
  id: number;
  medico?: number | null;
  reserva?: number | null;
  paciente?: number | null;
  centroSalud?: number | null;
  fechaHora?: dayjs.Dayjs | null;
  estado?: string | null;
  pacientes?: IPacientes | null;
  fkMedico?: IMedico | null;
  centrosSalud?: ICentrosSalud | null;
}

export type NewReserva = Omit<IReserva, 'id'> & { id: null };
