import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServiceHospital, ServiceHospital } from 'app/shared/model/service-hospital.model';
import { ServiceHospitalService } from './service-hospital.service';
import { ServiceHospitalComponent } from './service-hospital.component';
import { ServiceHospitalDetailComponent } from './service-hospital-detail.component';
import { ServiceHospitalUpdateComponent } from './service-hospital-update.component';

@Injectable({ providedIn: 'root' })
export class ServiceHospitalResolve implements Resolve<IServiceHospital> {
  constructor(private service: ServiceHospitalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceHospital> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serviceHospital: HttpResponse<ServiceHospital>) => {
          if (serviceHospital.body) {
            return of(serviceHospital.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServiceHospital());
  }
}

export const serviceHospitalRoute: Routes = [
  {
    path: '',
    component: ServiceHospitalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ServiceHospitals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceHospitalDetailComponent,
    resolve: {
      serviceHospital: ServiceHospitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceHospitals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceHospitalUpdateComponent,
    resolve: {
      serviceHospital: ServiceHospitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceHospitals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceHospitalUpdateComponent,
    resolve: {
      serviceHospital: ServiceHospitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceHospitals'
    },
    canActivate: [UserRouteAccessService]
  }
];
