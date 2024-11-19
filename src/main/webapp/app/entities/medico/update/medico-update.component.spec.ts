import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ICentrosSalud } from 'app/entities/centros-salud/centros-salud.model';
import { CentrosSaludService } from 'app/entities/centros-salud/service/centros-salud.service';
import { MedicoService } from '../service/medico.service';
import { IMedico } from '../medico.model';
import { MedicoFormService } from './medico-form.service';

import { MedicoUpdateComponent } from './medico-update.component';

describe('Medico Management Update Component', () => {
  let comp: MedicoUpdateComponent;
  let fixture: ComponentFixture<MedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let medicoFormService: MedicoFormService;
  let medicoService: MedicoService;
  let centrosSaludService: CentrosSaludService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MedicoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MedicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MedicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    medicoFormService = TestBed.inject(MedicoFormService);
    medicoService = TestBed.inject(MedicoService);
    centrosSaludService = TestBed.inject(CentrosSaludService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call centrosSalud query and add missing value', () => {
      const medico: IMedico = { id: 456 };
      const centrosSalud: ICentrosSalud = { id: 23575 };
      medico.centrosSalud = centrosSalud;

      const centrosSaludCollection: ICentrosSalud[] = [{ id: 21166 }];
      jest.spyOn(centrosSaludService, 'query').mockReturnValue(of(new HttpResponse({ body: centrosSaludCollection })));
      const expectedCollection: ICentrosSalud[] = [centrosSalud, ...centrosSaludCollection];
      jest.spyOn(centrosSaludService, 'addCentrosSaludToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ medico });
      comp.ngOnInit();

      expect(centrosSaludService.query).toHaveBeenCalled();
      expect(centrosSaludService.addCentrosSaludToCollectionIfMissing).toHaveBeenCalledWith(centrosSaludCollection, centrosSalud);
      expect(comp.centrosSaludsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const medico: IMedico = { id: 456 };
      const centrosSalud: ICentrosSalud = { id: 22091 };
      medico.centrosSalud = centrosSalud;

      activatedRoute.data = of({ medico });
      comp.ngOnInit();

      expect(comp.centrosSaludsCollection).toContain(centrosSalud);
      expect(comp.medico).toEqual(medico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMedico>>();
      const medico = { id: 123 };
      jest.spyOn(medicoFormService, 'getMedico').mockReturnValue(medico);
      jest.spyOn(medicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: medico }));
      saveSubject.complete();

      // THEN
      expect(medicoFormService.getMedico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(medicoService.update).toHaveBeenCalledWith(expect.objectContaining(medico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMedico>>();
      const medico = { id: 123 };
      jest.spyOn(medicoFormService, 'getMedico').mockReturnValue({ id: null });
      jest.spyOn(medicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: medico }));
      saveSubject.complete();

      // THEN
      expect(medicoFormService.getMedico).toHaveBeenCalled();
      expect(medicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMedico>>();
      const medico = { id: 123 };
      jest.spyOn(medicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(medicoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCentrosSalud', () => {
      it('Should forward to centrosSaludService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(centrosSaludService, 'compareCentrosSalud');
        comp.compareCentrosSalud(entity, entity2);
        expect(centrosSaludService.compareCentrosSalud).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
