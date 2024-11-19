import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPacientes } from '../pacientes.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../pacientes.test-samples';

import { PacientesService, RestPacientes } from './pacientes.service';

const requireRestSample: RestPacientes = {
  ...sampleWithRequiredData,
  fechaNacimiento: sampleWithRequiredData.fechaNacimiento?.format(DATE_FORMAT),
};

describe('Pacientes Service', () => {
  let service: PacientesService;
  let httpMock: HttpTestingController;
  let expectedResult: IPacientes | IPacientes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PacientesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Pacientes', () => {
      const pacientes = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pacientes).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pacientes', () => {
      const pacientes = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pacientes).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Pacientes', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Pacientes', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Pacientes', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPacientesToCollectionIfMissing', () => {
      it('should add a Pacientes to an empty array', () => {
        const pacientes: IPacientes = sampleWithRequiredData;
        expectedResult = service.addPacientesToCollectionIfMissing([], pacientes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pacientes);
      });

      it('should not add a Pacientes to an array that contains it', () => {
        const pacientes: IPacientes = sampleWithRequiredData;
        const pacientesCollection: IPacientes[] = [
          {
            ...pacientes,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPacientesToCollectionIfMissing(pacientesCollection, pacientes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pacientes to an array that doesn't contain it", () => {
        const pacientes: IPacientes = sampleWithRequiredData;
        const pacientesCollection: IPacientes[] = [sampleWithPartialData];
        expectedResult = service.addPacientesToCollectionIfMissing(pacientesCollection, pacientes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pacientes);
      });

      it('should add only unique Pacientes to an array', () => {
        const pacientesArray: IPacientes[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pacientesCollection: IPacientes[] = [sampleWithRequiredData];
        expectedResult = service.addPacientesToCollectionIfMissing(pacientesCollection, ...pacientesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pacientes: IPacientes = sampleWithRequiredData;
        const pacientes2: IPacientes = sampleWithPartialData;
        expectedResult = service.addPacientesToCollectionIfMissing([], pacientes, pacientes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pacientes);
        expect(expectedResult).toContain(pacientes2);
      });

      it('should accept null and undefined values', () => {
        const pacientes: IPacientes = sampleWithRequiredData;
        expectedResult = service.addPacientesToCollectionIfMissing([], null, pacientes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pacientes);
      });

      it('should return initial array if no Pacientes is added', () => {
        const pacientesCollection: IPacientes[] = [sampleWithRequiredData];
        expectedResult = service.addPacientesToCollectionIfMissing(pacientesCollection, undefined, null);
        expect(expectedResult).toEqual(pacientesCollection);
      });
    });

    describe('comparePacientes', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePacientes(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePacientes(entity1, entity2);
        const compareResult2 = service.comparePacientes(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePacientes(entity1, entity2);
        const compareResult2 = service.comparePacientes(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePacientes(entity1, entity2);
        const compareResult2 = service.comparePacientes(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
