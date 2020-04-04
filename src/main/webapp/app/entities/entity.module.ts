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
        path: 'bed',
        loadChildren: () => import('./bed/bed.module').then(m => m.CovidBedModule)
      },
      {
        path: 'bed-status-history',
        loadChildren: () => import('./bed-status-history/bed-status-history.module').then(m => m.CovidBedStatusHistoryModule)
      },
      {
        path: 'service-hospital',
        loadChildren: () => import('./service-hospital/service-hospital.module').then(m => m.CovidServiceHospitalModule)
      },
      {
        path: 'service-room',
        loadChildren: () => import('./service-room/service-room.module').then(m => m.CovidServiceRoomModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CovidEntityModule {}
