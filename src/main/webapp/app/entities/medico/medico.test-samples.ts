import { IMedico, NewMedico } from './medico.model';

export const sampleWithRequiredData: IMedico = {
  id: 551,
  medicoId: 189,
  nombre: 'pish swiftly',
  apellidoPaterno: 'punctually aha',
  telefono: 'on readily indeed',
  correo: 'openly stir-fry',
  centroSaludId: 645,
};

export const sampleWithPartialData: IMedico = {
  id: 20603,
  medicoId: 31671,
  nombre: 'sleepily degenerate',
  apellidoPaterno: 'over',
  apellidoMaterno: 'agreement',
  especialidad: 'who',
  telefono: 'indelible economise questionably',
  correo: 'aw',
  centroSaludId: 1783,
};

export const sampleWithFullData: IMedico = {
  id: 6821,
  medicoId: 6089,
  nombre: 'speedily geez that',
  apellidoPaterno: 'along',
  apellidoMaterno: 'apropos tidy very',
  especialidad: 'put mainstream',
  telefono: 'whoa',
  correo: 'however near',
  centroSaludId: 15431,
};

export const sampleWithNewData: NewMedico = {
  medicoId: 11758,
  nombre: 'meanwhile tough shrill',
  apellidoPaterno: 'than kissingly',
  telefono: 'clear-cut',
  correo: 'optimistically fess',
  centroSaludId: 19506,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
