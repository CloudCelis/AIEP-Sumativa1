import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPacientes, NewPacientes } from '../pacientes.model';

export type PartialUpdatePacientes = Partial<IPacientes> & Pick<IPacientes, 'id'>;

type RestOf<T extends IPacientes | NewPacientes> = Omit<T, 'fechaNacimiento'> & {
  fechaNacimiento?: string | null;
};

export type RestPacientes = RestOf<IPacientes>;

export type NewRestPacientes = RestOf<NewPacientes>;

export type PartialUpdateRestPacientes = RestOf<PartialUpdatePacientes>;

export type EntityResponseType = HttpResponse<IPacientes>;
export type EntityArrayResponseType = HttpResponse<IPacientes[]>;

@Injectable({ providedIn: 'root' })
export class PacientesService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pacientes');

  create(pacientes: NewPacientes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pacientes);
    return this.http
      .post<RestPacientes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(pacientes: IPacientes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pacientes);
    return this.http
      .put<RestPacientes>(`${this.resourceUrl}/${this.getPacientesIdentifier(pacientes)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(pacientes: PartialUpdatePacientes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pacientes);
    return this.http
      .patch<RestPacientes>(`${this.resourceUrl}/${this.getPacientesIdentifier(pacientes)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPacientes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPacientes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPacientesIdentifier(pacientes: Pick<IPacientes, 'id'>): number {
    return pacientes.id;
  }

  comparePacientes(o1: Pick<IPacientes, 'id'> | null, o2: Pick<IPacientes, 'id'> | null): boolean {
    return o1 && o2 ? this.getPacientesIdentifier(o1) === this.getPacientesIdentifier(o2) : o1 === o2;
  }

  addPacientesToCollectionIfMissing<Type extends Pick<IPacientes, 'id'>>(
    pacientesCollection: Type[],
    ...pacientesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pacientes: Type[] = pacientesToCheck.filter(isPresent);
    if (pacientes.length > 0) {
      const pacientesCollectionIdentifiers = pacientesCollection.map(pacientesItem => this.getPacientesIdentifier(pacientesItem));
      const pacientesToAdd = pacientes.filter(pacientesItem => {
        const pacientesIdentifier = this.getPacientesIdentifier(pacientesItem);
        if (pacientesCollectionIdentifiers.includes(pacientesIdentifier)) {
          return false;
        }
        pacientesCollectionIdentifiers.push(pacientesIdentifier);
        return true;
      });
      return [...pacientesToAdd, ...pacientesCollection];
    }
    return pacientesCollection;
  }

  protected convertDateFromClient<T extends IPacientes | NewPacientes | PartialUpdatePacientes>(pacientes: T): RestOf<T> {
    return {
      ...pacientes,
      fechaNacimiento: pacientes.fechaNacimiento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPacientes: RestPacientes): IPacientes {
    return {
      ...restPacientes,
      fechaNacimiento: restPacientes.fechaNacimiento ? dayjs(restPacientes.fechaNacimiento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPacientes>): HttpResponse<IPacientes> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPacientes[]>): HttpResponse<IPacientes[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
