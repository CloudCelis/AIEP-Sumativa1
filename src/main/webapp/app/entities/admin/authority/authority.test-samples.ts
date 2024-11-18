import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '3dd695c5-2c7d-4308-ae5c-8a27d3e4f978',
};

export const sampleWithPartialData: IAuthority = {
  name: '1a313533-0ba8-4c99-be61-a34c62843b28',
};

export const sampleWithFullData: IAuthority = {
  name: '8a295431-943f-4a9d-8cad-6c9359bdb3d9',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
