import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { ProfileComponent } from './profile.component';
import { ProfileDetailComponent } from './profile-detail.component';
import { ProfileUpdateComponent } from './profile-update.component';

@Injectable({ providedIn: 'root' })
export class ProfileResolve implements Resolve<IProfile> {
  constructor(private service: ProfileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((profile: HttpResponse<Profile>) => {
          if (profile.body) {
            return of(profile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Profile());
  }
}

export const profileRoute: Routes = [
  {
    path: '',
    component: ProfileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Profiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfileDetailComponent,
    resolve: {
      profile: ProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Profiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfileUpdateComponent,
    resolve: {
      profile: ProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Profiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfileUpdateComponent,
    resolve: {
      profile: ProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Profiles'
    },
    canActivate: [UserRouteAccessService]
  }
];
