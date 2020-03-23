import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'hospital',
        loadChildren: () => import('./hospital/hospital.module').then(m => m.CovidHospitalModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.CovidAddressModule)
      },
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.CovidProfileModule)
      },
      {
        path: 'bed',
        loadChildren: () => import('./bed/bed.module').then(m => m.CovidBedModule)
      },
      {
        path: 'bed-status-history',
        loadChildren: () => import('./bed-status-history/bed-status-history.module').then(m => m.CovidBedStatusHistoryModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CovidEntityModule {}
