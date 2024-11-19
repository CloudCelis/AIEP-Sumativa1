import { ICentrosSalud, NewCentrosSalud } from './centros-salud.model';

export const sampleWithRequiredData: ICentrosSalud = {
  id: 18208,
  centroSaludID: 8652,
  nombre: 'procurement thread acquaintance',
  direccion: 'consequently',
};

export const sampleWithPartialData: ICentrosSalud = {
  id: 31251,
  centroSaludID: 22220,
  nombre: 'cavernous minus as',
  direccion: 'deprave narrow',
  telefono: 'frilly oh',
  vigente: 's',
};

export const sampleWithFullData: ICentrosSalud = {
  id: 32384,
  centroSaludID: 21155,
  nombre: 'up',
  direccion: 'duh',
  telefono: 'but guacamole lest',
  vigente: 't',
};

export const sampleWithNewData: NewCentrosSalud = {
  centroSaludID: 2338,
  nombre: 'gosh',
  direccion: 'confusion seriously',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
