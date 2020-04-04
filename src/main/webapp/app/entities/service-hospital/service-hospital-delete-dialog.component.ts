import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceHospital } from 'app/shared/model/service-hospital.model';
import { ServiceHospitalService } from './service-hospital.service';

@Component({
  templateUrl: './service-hospital-delete-dialog.component.html'
})
export class ServiceHospitalDeleteDialogComponent {
  serviceHospital?: IServiceHospital;

  constructor(
    protected serviceHospitalService: ServiceHospitalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.serviceHospitalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serviceHospitalListModification');
      this.activeModal.close();
    });
  }
}
