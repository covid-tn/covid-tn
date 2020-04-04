import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServiceRoom, ServiceRoom } from 'app/shared/model/service-room.model';
import { ServiceRoomService } from './service-room.service';
import { ServiceRoomComponent } from './service-room.component';
import { ServiceRoomDetailComponent } from './service-room-detail.component';
import { ServiceRoomUpdateComponent } from './service-room-update.component';

@Injectable({ providedIn: 'root' })
export class ServiceRoomResolve implements Resolve<IServiceRoom> {
  constructor(private service: ServiceRoomService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceRoom> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serviceRoom: HttpResponse<ServiceRoom>) => {
          if (serviceRoom.body) {
            return of(serviceRoom.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServiceRoom());
  }
}

export const serviceRoomRoute: Routes = [
  {
    path: '',
    component: ServiceRoomComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ServiceRooms'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceRoomDetailComponent,
    resolve: {
      serviceRoom: ServiceRoomResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceRooms'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceRoomUpdateComponent,
    resolve: {
      serviceRoom: ServiceRoomResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceRooms'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceRoomUpdateComponent,
    resolve: {
      serviceRoom: ServiceRoomResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceRooms'
    },
    canActivate: [UserRouteAccessService]
  }
];
