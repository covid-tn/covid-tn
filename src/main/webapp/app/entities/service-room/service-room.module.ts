import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidSharedModule } from 'app/shared/shared.module';
import { ServiceRoomComponent } from './service-room.component';
import { ServiceRoomDetailComponent } from './service-room-detail.component';
import { ServiceRoomUpdateComponent } from './service-room-update.component';
import { ServiceRoomDeleteDialogComponent } from './service-room-delete-dialog.component';
import { serviceRoomRoute } from './service-room.route';

@NgModule({
  imports: [CovidSharedModule, RouterModule.forChild(serviceRoomRoute)],
  declarations: [ServiceRoomComponent, ServiceRoomDetailComponent, ServiceRoomUpdateComponent, ServiceRoomDeleteDialogComponent],
  entryComponents: [ServiceRoomDeleteDialogComponent]
})
export class CovidServiceRoomModule {}
