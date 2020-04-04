import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServiceHospital } from 'app/shared/model/service-hospital.model';

type EntityResponseType = HttpResponse<IServiceHospital>;
type EntityArrayResponseType = HttpResponse<IServiceHospital[]>;

@Injectable({ providedIn: 'root' })
export class ServiceHospitalService {
  public resourceUrl = SERVER_API_URL + 'api/service-hospitals';

  constructor(protected http: HttpClient) {}

  create(serviceHospital: IServiceHospital): Observable<EntityResponseType> {
    return this.http.post<IServiceHospital>(this.resourceUrl, serviceHospital, { observe: 'response' });
  }

  update(serviceHospital: IServiceHospital): Observable<EntityResponseType> {
    return this.http.put<IServiceHospital>(this.resourceUrl, serviceHospital, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IServiceHospital>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceHospital[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
