import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IServiceRoom, ServiceRoom } from 'app/shared/model/service-room.model';
import { ServiceRoomService } from './service-room.service';
import { IServiceHospital } from 'app/shared/model/service-hospital.model';
import { ServiceHospitalService } from 'app/entities/service-hospital/service-hospital.service';

@Component({
  selector: 'jhi-service-room-update',
  templateUrl: './service-room-update.component.html'
})
export class ServiceRoomUpdateComponent implements OnInit {
  isSaving = false;

  servicehospitals: IServiceHospital[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    serviceHospital: []
  });

  constructor(
    protected serviceRoomService: ServiceRoomService,
    protected serviceHospitalService: ServiceHospitalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceRoom }) => {
      this.updateForm(serviceRoom);

      this.serviceHospitalService
        .query()
        .pipe(
          map((res: HttpResponse<IServiceHospital[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IServiceHospital[]) => (this.servicehospitals = resBody));
    });
  }

  updateForm(serviceRoom: IServiceRoom): void {
    this.editForm.patchValue({
      id: serviceRoom.id,
      name: serviceRoom.name,
      description: serviceRoom.description,
      serviceHospital: serviceRoom.serviceHospital
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceRoom = this.createFromForm();
    if (serviceRoom.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceRoomService.update(serviceRoom));
    } else {
      this.subscribeToSaveResponse(this.serviceRoomService.create(serviceRoom));
    }
  }

  private createFromForm(): IServiceRoom {
    return {
      ...new ServiceRoom(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      serviceHospital: this.editForm.get(['serviceHospital'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceRoom>>): void {
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

  trackById(index: number, item: IServiceHospital): any {
    return item.id;
  }
}
