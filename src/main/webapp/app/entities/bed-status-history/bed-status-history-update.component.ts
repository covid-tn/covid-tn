import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBedStatusHistory, BedStatusHistory } from 'app/shared/model/bed-status-history.model';
import { BedStatusHistoryService } from './bed-status-history.service';
import { IBed } from 'app/shared/model/bed.model';
import { BedService } from 'app/entities/bed/bed.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IBed | IUser;

@Component({
  selector: 'jhi-bed-status-history-update',
  templateUrl: './bed-status-history-update.component.html'
})
export class BedStatusHistoryUpdateComponent implements OnInit {
  isSaving = false;

  beds: IBed[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    createdDate: [],
    bed: [],
    createdBy: []
  });

  constructor(
    protected bedStatusHistoryService: BedStatusHistoryService,
    protected bedService: BedService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedStatusHistory }) => {
      this.updateForm(bedStatusHistory);

      this.bedService
        .query({ filter: 'bedstatushistory-is-null' })
        .pipe(
          map((res: HttpResponse<IBed[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IBed[]) => {
          if (!bedStatusHistory.bed || !bedStatusHistory.bed.id) {
            this.beds = resBody;
          } else {
            this.bedService
              .find(bedStatusHistory.bed.id)
              .pipe(
                map((subRes: HttpResponse<IBed>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBed[]) => {
                this.beds = concatRes;
              });
          }
        });

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(bedStatusHistory: IBedStatusHistory): void {
    this.editForm.patchValue({
      id: bedStatusHistory.id,
      createdDate: bedStatusHistory.createdDate != null ? bedStatusHistory.createdDate.format(DATE_TIME_FORMAT) : null,
      bed: bedStatusHistory.bed,
      createdBy: bedStatusHistory.createdBy
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bedStatusHistory = this.createFromForm();
    if (bedStatusHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.bedStatusHistoryService.update(bedStatusHistory));
    } else {
      this.subscribeToSaveResponse(this.bedStatusHistoryService.create(bedStatusHistory));
    }
  }

  private createFromForm(): IBedStatusHistory {
    return {
      ...new BedStatusHistory(),
      id: this.editForm.get(['id'])!.value,
      createdDate:
        this.editForm.get(['createdDate'])!.value != null ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT) : undefined,
      bed: this.editForm.get(['bed'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBedStatusHistory>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
