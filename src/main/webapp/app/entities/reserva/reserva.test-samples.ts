import dayjs from 'dayjs/esm';

import { IReserva, NewReserva } from './reserva.model';

export const sampleWithRequiredData: IReserva = {
  id: 9399,
};

export const sampleWithPartialData: IReserva = {
  id: 19893,
  reserva: 12847,
  centroSalud: 9098,
  fechaHora: dayjs('2024-11-19'),
  estado: 'gripping rewarding angle',
};

export const sampleWithFullData: IReserva = {
  id: 25919,
  medico: 25520,
  reserva: 31303,
  paciente: 2648,
  centroSalud: 26214,
  fechaHora: dayjs('2024-11-19'),
  estado: 'publication meh hundred',
};

export const sampleWithNewData: NewReserva = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
