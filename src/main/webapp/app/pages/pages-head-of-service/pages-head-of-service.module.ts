import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidSharedModule } from 'app/shared/shared.module';
import { headOfServicelRoute } from './pages-head-of-service.route';
import { HosBedsComponent } from 'app/pages/pages-head-of-service/beds/hos-beds.component';
import { HosDoctorsComponent } from 'app/pages/pages-head-of-service/doctors/hos-doctors.component';
import { HosBedsDeleteDialogComponent } from 'app/pages/pages-head-of-service/beds/hos-beds-delete-dialog.component';

@NgModule({
  imports: [CovidSharedModule, RouterModule.forChild(headOfServicelRoute)],
  declarations: [HosBedsComponent, HosDoctorsComponent, HosBedsDeleteDialogComponent],
  entryComponents: [HosBedsDeleteDialogComponent]
})
export class PagesHeadOfServiceModule {}
