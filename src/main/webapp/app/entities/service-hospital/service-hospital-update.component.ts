import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IServiceHospital, ServiceHospital } from 'app/shared/model/service-hospital.model';
import { ServiceHospitalService } from './service-hospital.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital/hospital.service';

@Component({
  selector: 'jhi-service-hospital-update',
  templateUrl: './service-hospital-update.component.html'
})
export class ServiceHospitalUpdateComponent implements OnInit {
  isSaving = false;

  hospitals: IHospital[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    hospital: []
  });

  constructor(
    protected serviceHospitalService: ServiceHospitalService,
    protected hospitalService: HospitalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceHospital }) => {
      this.updateForm(serviceHospital);

      this.hospitalService
        .query()
        .pipe(
          map((res: HttpResponse<IHospital[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IHospital[]) => (this.hospitals = resBody));
    });
  }

  updateForm(serviceHospital: IServiceHospital): void {
    this.editForm.patchValue({
      id: serviceHospital.id,
      name: serviceHospital.name,
      description: serviceHospital.description,
      hospital: serviceHospital.hospital
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceHospital = this.createFromForm();
    if (serviceHospital.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceHospitalService.update(serviceHospital));
    } else {
      this.subscribeToSaveResponse(this.serviceHospitalService.create(serviceHospital));
    }
  }

  private createFromForm(): IServiceHospital {
    return {
      ...new ServiceHospital(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      hospital: this.editForm.get(['hospital'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceHospital>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IHospital): any {
    return item.id;
  }
}
