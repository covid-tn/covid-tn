import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceHospital } from 'app/shared/model/service-hospital.model';

@Component({
  selector: 'jhi-service-hospital-detail',
  templateUrl: './service-hospital-detail.component.html'
})
export class ServiceHospitalDetailComponent implements OnInit {
  serviceHospital: IServiceHospital | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceHospital }) => {
      this.serviceHospital = serviceHospital;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
