import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICentrosSalud, NewCentrosSalud } from '../centros-salud.model';

export type PartialUpdateCentrosSalud = Partial<ICentrosSalud> & Pick<ICentrosSalud, 'id'>;

export type EntityResponseType = HttpResponse<ICentrosSalud>;
export type EntityArrayResponseType = HttpResponse<ICentrosSalud[]>;

@Injectable({ providedIn: 'root' })
export class CentrosSaludService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/centros-saluds');

  create(centrosSalud: NewCentrosSalud): Observable<EntityResponseType> {
    return this.http.post<ICentrosSalud>(this.resourceUrl, centrosSalud, { observe: 'response' });
  }

  update(centrosSalud: ICentrosSalud): Observable<EntityResponseType> {
    return this.http.put<ICentrosSalud>(`${this.resourceUrl}/${this.getCentrosSaludIdentifier(centrosSalud)}`, centrosSalud, {
      observe: 'response',
    });
  }

  partialUpdate(centrosSalud: PartialUpdateCentrosSalud): Observable<EntityResponseType> {
    return this.http.patch<ICentrosSalud>(`${this.resourceUrl}/${this.getCentrosSaludIdentifier(centrosSalud)}`, centrosSalud, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICentrosSalud>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICentrosSalud[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCentrosSaludIdentifier(centrosSalud: Pick<ICentrosSalud, 'id'>): number {
    return centrosSalud.id;
  }

  compareCentrosSalud(o1: Pick<ICentrosSalud, 'id'> | null, o2: Pick<ICentrosSalud, 'id'> | null): boolean {
    return o1 && o2 ? this.getCentrosSaludIdentifier(o1) === this.getCentrosSaludIdentifier(o2) : o1 === o2;
  }

  addCentrosSaludToCollectionIfMissing<Type extends Pick<ICentrosSalud, 'id'>>(
    centrosSaludCollection: Type[],
    ...centrosSaludsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const centrosSaluds: Type[] = centrosSaludsToCheck.filter(isPresent);
    if (centrosSaluds.length > 0) {
      const centrosSaludCollectionIdentifiers = centrosSaludCollection.map(centrosSaludItem =>
        this.getCentrosSaludIdentifier(centrosSaludItem),
      );
      const centrosSaludsToAdd = centrosSaluds.filter(centrosSaludItem => {
        const centrosSaludIdentifier = this.getCentrosSaludIdentifier(centrosSaludItem);
        if (centrosSaludCollectionIdentifiers.includes(centrosSaludIdentifier)) {
          return false;
        }
        centrosSaludCollectionIdentifiers.push(centrosSaludIdentifier);
        return true;
      });
      return [...centrosSaludsToAdd, ...centrosSaludCollection];
    }
    return centrosSaludCollection;
  }
}
