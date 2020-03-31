import { Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { HosBedsComponent } from 'app/pages/pages-head-of-service/beds/hos-beds.component';
import { HosDoctorsComponent } from 'app/pages/pages-head-of-service/doctors/hos-doctors.component';

export const headOfServicelRoute: Routes = [
  {
    path: 'manage-beds',
    component: HosBedsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_HEAD_OF_SERVICE'],
      defaultSort: 'id,asc',
      pageTitle: 'Beds'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'manage-doctors',
    component: HosDoctorsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_HEAD_OF_SERVICE'],
      defaultSort: 'id,asc',
      pageTitle: 'Doctors'
    },
    canActivate: [UserRouteAccessService]
  }
];
