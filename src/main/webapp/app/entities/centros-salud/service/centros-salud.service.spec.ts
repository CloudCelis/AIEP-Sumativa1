import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICentrosSalud } from '../centros-salud.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../centros-salud.test-samples';

import { CentrosSaludService } from './centros-salud.service';

const requireRestSample: ICentrosSalud = {
  ...sampleWithRequiredData,
};

describe('CentrosSalud Service', () => {
  let service: CentrosSaludService;
  let httpMock: HttpTestingController;
  let expectedResult: ICentrosSalud | ICentrosSalud[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CentrosSaludService);
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

    it('should create a CentrosSalud', () => {
      const centrosSalud = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(centrosSalud).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CentrosSalud', () => {
      const centrosSalud = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(centrosSalud).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CentrosSalud', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CentrosSalud', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CentrosSalud', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCentrosSaludToCollectionIfMissing', () => {
      it('should add a CentrosSalud to an empty array', () => {
        const centrosSalud: ICentrosSalud = sampleWithRequiredData;
        expectedResult = service.addCentrosSaludToCollectionIfMissing([], centrosSalud);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centrosSalud);
      });

      it('should not add a CentrosSalud to an array that contains it', () => {
        const centrosSalud: ICentrosSalud = sampleWithRequiredData;
        const centrosSaludCollection: ICentrosSalud[] = [
          {
            ...centrosSalud,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCentrosSaludToCollectionIfMissing(centrosSaludCollection, centrosSalud);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CentrosSalud to an array that doesn't contain it", () => {
        const centrosSalud: ICentrosSalud = sampleWithRequiredData;
        const centrosSaludCollection: ICentrosSalud[] = [sampleWithPartialData];
        expectedResult = service.addCentrosSaludToCollectionIfMissing(centrosSaludCollection, centrosSalud);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centrosSalud);
      });

      it('should add only unique CentrosSalud to an array', () => {
        const centrosSaludArray: ICentrosSalud[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const centrosSaludCollection: ICentrosSalud[] = [sampleWithRequiredData];
        expectedResult = service.addCentrosSaludToCollectionIfMissing(centrosSaludCollection, ...centrosSaludArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const centrosSalud: ICentrosSalud = sampleWithRequiredData;
        const centrosSalud2: ICentrosSalud = sampleWithPartialData;
        expectedResult = service.addCentrosSaludToCollectionIfMissing([], centrosSalud, centrosSalud2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centrosSalud);
        expect(expectedResult).toContain(centrosSalud2);
      });

      it('should accept null and undefined values', () => {
        const centrosSalud: ICentrosSalud = sampleWithRequiredData;
        expectedResult = service.addCentrosSaludToCollectionIfMissing([], null, centrosSalud, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centrosSalud);
      });

      it('should return initial array if no CentrosSalud is added', () => {
        const centrosSaludCollection: ICentrosSalud[] = [sampleWithRequiredData];
        expectedResult = service.addCentrosSaludToCollectionIfMissing(centrosSaludCollection, undefined, null);
        expect(expectedResult).toEqual(centrosSaludCollection);
      });
    });

    describe('compareCentrosSalud', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCentrosSalud(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCentrosSalud(entity1, entity2);
        const compareResult2 = service.compareCentrosSalud(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCentrosSalud(entity1, entity2);
        const compareResult2 = service.compareCentrosSalud(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCentrosSalud(entity1, entity2);
        const compareResult2 = service.compareCentrosSalud(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
