import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidSharedModule } from 'app/shared/shared.module';
import { ServiceHospitalComponent } from './service-hospital.component';
import { ServiceHospitalDetailComponent } from './service-hospital-detail.component';
import { ServiceHospitalUpdateComponent } from './service-hospital-update.component';
import { ServiceHospitalDeleteDialogComponent } from './service-hospital-delete-dialog.component';
import { serviceHospitalRoute } from './service-hospital.route';

@NgModule({
  imports: [CovidSharedModule, RouterModule.forChild(serviceHospitalRoute)],
  declarations: [
    ServiceHospitalComponent,
    ServiceHospitalDetailComponent,
    ServiceHospitalUpdateComponent,
    ServiceHospitalDeleteDialogComponent
  ],
  entryComponents: [ServiceHospitalDeleteDialogComponent]
})
export class CovidServiceHospitalModule {}
