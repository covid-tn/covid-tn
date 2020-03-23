import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidSharedModule } from 'app/shared/shared.module';
import { BedComponent } from './bed.component';
import { BedDetailComponent } from './bed-detail.component';
import { BedUpdateComponent } from './bed-update.component';
import { BedDeleteDialogComponent } from './bed-delete-dialog.component';
import { bedRoute } from './bed.route';

@NgModule({
  imports: [CovidSharedModule, RouterModule.forChild(bedRoute)],
  declarations: [BedComponent, BedDetailComponent, BedUpdateComponent, BedDeleteDialogComponent],
  entryComponents: [BedDeleteDialogComponent]
})
export class CovidBedModule {}
