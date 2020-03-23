import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidSharedModule } from 'app/shared/shared.module';
import { BedStatusHistoryComponent } from './bed-status-history.component';
import { BedStatusHistoryDetailComponent } from './bed-status-history-detail.component';
import { BedStatusHistoryUpdateComponent } from './bed-status-history-update.component';
import { BedStatusHistoryDeleteDialogComponent } from './bed-status-history-delete-dialog.component';
import { bedStatusHistoryRoute } from './bed-status-history.route';

@NgModule({
  imports: [CovidSharedModule, RouterModule.forChild(bedStatusHistoryRoute)],
  declarations: [
    BedStatusHistoryComponent,
    BedStatusHistoryDetailComponent,
    BedStatusHistoryUpdateComponent,
    BedStatusHistoryDeleteDialogComponent
  ],
  entryComponents: [BedStatusHistoryDeleteDialogComponent]
})
export class CovidBedStatusHistoryModule {}
