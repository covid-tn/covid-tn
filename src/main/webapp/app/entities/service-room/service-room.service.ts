import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServiceRoom } from 'app/shared/model/service-room.model';

type EntityResponseType = HttpResponse<IServiceRoom>;
type EntityArrayResponseType = HttpResponse<IServiceRoom[]>;

@Injectable({ providedIn: 'root' })
export class ServiceRoomService {
  public resourceUrl = SERVER_API_URL + 'api/service-rooms';

  constructor(protected http: HttpClient) {}

  create(serviceRoom: IServiceRoom): Observable<EntityResponseType> {
    return this.http.post<IServiceRoom>(this.resourceUrl, serviceRoom, { observe: 'response' });
  }

  update(serviceRoom: IServiceRoom): Observable<EntityResponseType> {
    return this.http.put<IServiceRoom>(this.resourceUrl, serviceRoom, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IServiceRoom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceRoom[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
