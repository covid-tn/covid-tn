import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceRoom } from 'app/shared/model/service-room.model';
import { ServiceRoomService } from './service-room.service';

@Component({
  templateUrl: './service-room-delete-dialog.component.html'
})
export class ServiceRoomDeleteDialogComponent {
  serviceRoom?: IServiceRoom;

  constructor(
    protected serviceRoomService: ServiceRoomService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.serviceRoomService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serviceRoomListModification');
      this.activeModal.close();
    });
  }
}
