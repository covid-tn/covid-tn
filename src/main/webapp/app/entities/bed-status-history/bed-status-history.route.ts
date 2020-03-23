import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBedStatusHistory, BedStatusHistory } from 'app/shared/model/bed-status-history.model';
import { BedStatusHistoryService } from './bed-status-history.service';
import { BedStatusHistoryComponent } from './bed-status-history.component';
import { BedStatusHistoryDetailComponent } from './bed-status-history-detail.component';
import { BedStatusHistoryUpdateComponent } from './bed-status-history-update.component';

@Injectable({ providedIn: 'root' })
export class BedStatusHistoryResolve implements Resolve<IBedStatusHistory> {
  constructor(private service: BedStatusHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBedStatusHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bedStatusHistory: HttpResponse<BedStatusHistory>) => {
          if (bedStatusHistory.body) {
            return of(bedStatusHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BedStatusHistory());
  }
}

export const bedStatusHistoryRoute: Routes = [
  {
    path: '',
    component: BedStatusHistoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'BedStatusHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BedStatusHistoryDetailComponent,
    resolve: {
      bedStatusHistory: BedStatusHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BedStatusHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BedStatusHistoryUpdateComponent,
    resolve: {
      bedStatusHistory: BedStatusHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BedStatusHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BedStatusHistoryUpdateComponent,
    resolve: {
      bedStatusHistory: BedStatusHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BedStatusHistories'
    },
    canActivate: [UserRouteAccessService]
  }
];
