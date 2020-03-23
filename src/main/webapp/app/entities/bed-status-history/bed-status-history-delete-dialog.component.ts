import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBedStatusHistory } from 'app/shared/model/bed-status-history.model';
import { BedStatusHistoryService } from './bed-status-history.service';

@Component({
  templateUrl: './bed-status-history-delete-dialog.component.html'
})
export class BedStatusHistoryDeleteDialogComponent {
  bedStatusHistory?: IBedStatusHistory;

  constructor(
    protected bedStatusHistoryService: BedStatusHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.bedStatusHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bedStatusHistoryListModification');
      this.activeModal.close();
    });
  }
}
