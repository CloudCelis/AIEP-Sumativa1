import dayjs from 'dayjs/esm';

import { IPacientes, NewPacientes } from './pacientes.model';

export const sampleWithRequiredData: IPacientes = {
  id: 30300,
  pacienteId: 11402,
};

export const sampleWithPartialData: IPacientes = {
  id: 27608,
  pacienteId: 931,
  rut: 'interestingly',
  fechaNacimiento: dayjs('2024-11-18'),
  direccion: 'extremely aggravating hungrily',
};

export const sampleWithFullData: IPacientes = {
  id: 13401,
  pacienteId: 1410,
  nombre: 'expert mixture so',
  apellidoPaterno: 'widow',
  apellidoMaterno: 'softly remorseful',
  rut: 'electronics via',
  fechaNacimiento: dayjs('2024-11-19'),
  genero: 'easily aboard',
  telefono: 'sparkling',
  email: 'Agustin_QuintanaMarin@hotmail.com',
  direccion: 'compassionate',
};

export const sampleWithNewData: NewPacientes = {
  pacienteId: 15292,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
