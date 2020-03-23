import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBed, Bed } from 'app/shared/model/bed.model';
import { BedService } from './bed.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital/hospital.service';

@Component({
  selector: 'jhi-bed-update',
  templateUrl: './bed-update.component.html'
})
export class BedUpdateComponent implements OnInit {
  isSaving = false;

  hospitals: IHospital[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    hospital: []
  });

  constructor(
    protected bedService: BedService,
    protected hospitalService: HospitalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bed }) => {
      this.updateForm(bed);

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

  updateForm(bed: IBed): void {
    this.editForm.patchValue({
      id: bed.id,
      status: bed.status,
      hospital: bed.hospital
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bed = this.createFromForm();
    if (bed.id !== undefined) {
      this.subscribeToSaveResponse(this.bedService.update(bed));
    } else {
      this.subscribeToSaveResponse(this.bedService.create(bed));
    }
  }

  private createFromForm(): IBed {
    return {
      ...new Bed(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      hospital: this.editForm.get(['hospital'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBed>>): void {
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
