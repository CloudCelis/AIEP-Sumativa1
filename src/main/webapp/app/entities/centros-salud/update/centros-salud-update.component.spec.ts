import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CentrosSaludService } from '../service/centros-salud.service';
import { ICentrosSalud } from '../centros-salud.model';
import { CentrosSaludFormService } from './centros-salud-form.service';

import { CentrosSaludUpdateComponent } from './centros-salud-update.component';

describe('CentrosSalud Management Update Component', () => {
  let comp: CentrosSaludUpdateComponent;
  let fixture: ComponentFixture<CentrosSaludUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centrosSaludFormService: CentrosSaludFormService;
  let centrosSaludService: CentrosSaludService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CentrosSaludUpdateComponent],
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
      .overrideTemplate(CentrosSaludUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CentrosSaludUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centrosSaludFormService = TestBed.inject(CentrosSaludFormService);
    centrosSaludService = TestBed.inject(CentrosSaludService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const centrosSalud: ICentrosSalud = { id: 456 };

      activatedRoute.data = of({ centrosSalud });
      comp.ngOnInit();

      expect(comp.centrosSalud).toEqual(centrosSalud);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentrosSalud>>();
      const centrosSalud = { id: 123 };
      jest.spyOn(centrosSaludFormService, 'getCentrosSalud').mockReturnValue(centrosSalud);
      jest.spyOn(centrosSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centrosSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centrosSalud }));
      saveSubject.complete();

      // THEN
      expect(centrosSaludFormService.getCentrosSalud).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centrosSaludService.update).toHaveBeenCalledWith(expect.objectContaining(centrosSalud));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentrosSalud>>();
      const centrosSalud = { id: 123 };
      jest.spyOn(centrosSaludFormService, 'getCentrosSalud').mockReturnValue({ id: null });
      jest.spyOn(centrosSaludService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centrosSalud: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centrosSalud }));
      saveSubject.complete();

      // THEN
      expect(centrosSaludFormService.getCentrosSalud).toHaveBeenCalled();
      expect(centrosSaludService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentrosSalud>>();
      const centrosSalud = { id: 123 };
      jest.spyOn(centrosSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centrosSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centrosSaludService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
