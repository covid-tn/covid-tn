import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBedStatusHistory } from 'app/shared/model/bed-status-history.model';

type EntityResponseType = HttpResponse<IBedStatusHistory>;
type EntityArrayResponseType = HttpResponse<IBedStatusHistory[]>;

@Injectable({ providedIn: 'root' })
export class BedStatusHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/bed-status-histories';

  constructor(protected http: HttpClient) {}

  create(bedStatusHistory: IBedStatusHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedStatusHistory);
    return this.http
      .post<IBedStatusHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bedStatusHistory: IBedStatusHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedStatusHistory);
    return this.http
      .put<IBedStatusHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IBedStatusHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBedStatusHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bedStatusHistory: IBedStatusHistory): IBedStatusHistory {
    const copy: IBedStatusHistory = Object.assign({}, bedStatusHistory, {
      createdDate:
        bedStatusHistory.createdDate && bedStatusHistory.createdDate.isValid() ? bedStatusHistory.createdDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bedStatusHistory: IBedStatusHistory) => {
        bedStatusHistory.createdDate = bedStatusHistory.createdDate ? moment(bedStatusHistory.createdDate) : undefined;
      });
    }
    return res;
  }
}
