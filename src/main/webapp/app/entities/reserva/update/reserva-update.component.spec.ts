import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IPacientes } from 'app/entities/pacientes/pacientes.model';
import { PacientesService } from 'app/entities/pacientes/service/pacientes.service';
import { IMedico } from 'app/entities/medico/medico.model';
import { MedicoService } from 'app/entities/medico/service/medico.service';
import { ICentrosSalud } from 'app/entities/centros-salud/centros-salud.model';
import { CentrosSaludService } from 'app/entities/centros-salud/service/centros-salud.service';
import { IReserva } from '../reserva.model';
import { ReservaService } from '../service/reserva.service';
import { ReservaFormService } from './reserva-form.service';

import { ReservaUpdateComponent } from './reserva-update.component';

describe('Reserva Management Update Component', () => {
  let comp: ReservaUpdateComponent;
  let fixture: ComponentFixture<ReservaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reservaFormService: ReservaFormService;
  let reservaService: ReservaService;
  let pacientesService: PacientesService;
  let medicoService: MedicoService;
  let centrosSaludService: CentrosSaludService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReservaUpdateComponent],
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
      .overrideTemplate(ReservaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReservaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reservaFormService = TestBed.inject(ReservaFormService);
    reservaService = TestBed.inject(ReservaService);
    pacientesService = TestBed.inject(PacientesService);
    medicoService = TestBed.inject(MedicoService);
    centrosSaludService = TestBed.inject(CentrosSaludService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pacientes query and add missing value', () => {
      const reserva: IReserva = { id: 456 };
      const pacientes: IPacientes = { id: 16510 };
      reserva.pacientes = pacientes;

      const pacientesCollection: IPacientes[] = [{ id: 13473 }];
      jest.spyOn(pacientesService, 'query').mockReturnValue(of(new HttpResponse({ body: pacientesCollection })));
      const additionalPacientes = [pacientes];
      const expectedCollection: IPacientes[] = [...additionalPacientes, ...pacientesCollection];
      jest.spyOn(pacientesService, 'addPacientesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      expect(pacientesService.query).toHaveBeenCalled();
      expect(pacientesService.addPacientesToCollectionIfMissing).toHaveBeenCalledWith(
        pacientesCollection,
        ...additionalPacientes.map(expect.objectContaining),
      );
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Medico query and add missing value', () => {
      const reserva: IReserva = { id: 456 };
      const fkMedico: IMedico = { id: 4171 };
      reserva.fkMedico = fkMedico;

      const medicoCollection: IMedico[] = [{ id: 4330 }];
      jest.spyOn(medicoService, 'query').mockReturnValue(of(new HttpResponse({ body: medicoCollection })));
      const additionalMedicos = [fkMedico];
      const expectedCollection: IMedico[] = [...additionalMedicos, ...medicoCollection];
      jest.spyOn(medicoService, 'addMedicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      expect(medicoService.query).toHaveBeenCalled();
      expect(medicoService.addMedicoToCollectionIfMissing).toHaveBeenCalledWith(
        medicoCollection,
        ...additionalMedicos.map(expect.objectContaining),
      );
      expect(comp.medicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CentrosSalud query and add missing value', () => {
      const reserva: IReserva = { id: 456 };
      const centrosSalud: ICentrosSalud = { id: 1167 };
      reserva.centrosSalud = centrosSalud;

      const centrosSaludCollection: ICentrosSalud[] = [{ id: 18308 }];
      jest.spyOn(centrosSaludService, 'query').mockReturnValue(of(new HttpResponse({ body: centrosSaludCollection })));
      const additionalCentrosSaluds = [centrosSalud];
      const expectedCollection: ICentrosSalud[] = [...additionalCentrosSaluds, ...centrosSaludCollection];
      jest.spyOn(centrosSaludService, 'addCentrosSaludToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      expect(centrosSaludService.query).toHaveBeenCalled();
      expect(centrosSaludService.addCentrosSaludToCollectionIfMissing).toHaveBeenCalledWith(
        centrosSaludCollection,
        ...additionalCentrosSaluds.map(expect.objectContaining),
      );
      expect(comp.centrosSaludsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const reserva: IReserva = { id: 456 };
      const pacientes: IPacientes = { id: 31423 };
      reserva.pacientes = pacientes;
      const fkMedico: IMedico = { id: 30934 };
      reserva.fkMedico = fkMedico;
      const centrosSalud: ICentrosSalud = { id: 8620 };
      reserva.centrosSalud = centrosSalud;

      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      expect(comp.pacientesSharedCollection).toContain(pacientes);
      expect(comp.medicosSharedCollection).toContain(fkMedico);
      expect(comp.centrosSaludsSharedCollection).toContain(centrosSalud);
      expect(comp.reserva).toEqual(reserva);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReserva>>();
      const reserva = { id: 123 };
      jest.spyOn(reservaFormService, 'getReserva').mockReturnValue(reserva);
      jest.spyOn(reservaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reserva }));
      saveSubject.complete();

      // THEN
      expect(reservaFormService.getReserva).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reservaService.update).toHaveBeenCalledWith(expect.objectContaining(reserva));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReserva>>();
      const reserva = { id: 123 };
      jest.spyOn(reservaFormService, 'getReserva').mockReturnValue({ id: null });
      jest.spyOn(reservaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reserva: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reserva }));
      saveSubject.complete();

      // THEN
      expect(reservaFormService.getReserva).toHaveBeenCalled();
      expect(reservaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReserva>>();
      const reserva = { id: 123 };
      jest.spyOn(reservaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reservaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePacientes', () => {
      it('Should forward to pacientesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pacientesService, 'comparePacientes');
        comp.comparePacientes(entity, entity2);
        expect(pacientesService.comparePacientes).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMedico', () => {
      it('Should forward to medicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(medicoService, 'compareMedico');
        comp.compareMedico(entity, entity2);
        expect(medicoService.compareMedico).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
