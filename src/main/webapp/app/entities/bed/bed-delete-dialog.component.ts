import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBed } from 'app/shared/model/bed.model';
import { BedService } from './bed.service';

@Component({
  templateUrl: './bed-delete-dialog.component.html'
})
export class BedDeleteDialogComponent {
  bed?: IBed;

  constructor(protected bedService: BedService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.bedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bedListModification');
      this.activeModal.close();
    });
  }
}
