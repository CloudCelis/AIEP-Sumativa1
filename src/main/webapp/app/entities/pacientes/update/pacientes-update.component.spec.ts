import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PacientesService } from '../service/pacientes.service';
import { IPacientes } from '../pacientes.model';
import { PacientesFormService } from './pacientes-form.service';

import { PacientesUpdateComponent } from './pacientes-update.component';

describe('Pacientes Management Update Component', () => {
  let comp: PacientesUpdateComponent;
  let fixture: ComponentFixture<PacientesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pacientesFormService: PacientesFormService;
  let pacientesService: PacientesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PacientesUpdateComponent],
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
      .overrideTemplate(PacientesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PacientesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pacientesFormService = TestBed.inject(PacientesFormService);
    pacientesService = TestBed.inject(PacientesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pacientes: IPacientes = { id: 456 };

      activatedRoute.data = of({ pacientes });
      comp.ngOnInit();

      expect(comp.pacientes).toEqual(pacientes);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPacientes>>();
      const pacientes = { id: 123 };
      jest.spyOn(pacientesFormService, 'getPacientes').mockReturnValue(pacientes);
      jest.spyOn(pacientesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pacientes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pacientes }));
      saveSubject.complete();

      // THEN
      expect(pacientesFormService.getPacientes).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pacientesService.update).toHaveBeenCalledWith(expect.objectContaining(pacientes));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPacientes>>();
      const pacientes = { id: 123 };
      jest.spyOn(pacientesFormService, 'getPacientes').mockReturnValue({ id: null });
      jest.spyOn(pacientesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pacientes: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pacientes }));
      saveSubject.complete();

      // THEN
      expect(pacientesFormService.getPacientes).toHaveBeenCalled();
      expect(pacientesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPacientes>>();
      const pacientes = { id: 123 };
      jest.spyOn(pacientesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pacientes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pacientesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
