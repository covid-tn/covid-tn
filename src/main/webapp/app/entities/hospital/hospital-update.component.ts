import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IHospital, Hospital } from 'app/shared/model/hospital.model';
import { HospitalService } from './hospital.service';
import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IAddress | IUser;

@Component({
  selector: 'jhi-hospital-update',
  templateUrl: './hospital-update.component.html'
})
export class HospitalUpdateComponent implements OnInit {
  isSaving = false;

  addresses: IAddress[] = [];

  users: IUser[] = [];

  //  To hide add address form
  addedaddress = false;

  newstreetname: string | undefined;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    address: [null, Validators.required],
    headOfSearvice: []
  });

  addaddressform = this.fb.group({
    id: [],
    streetName: [null, [Validators.required]],
    city: [null, [Validators.required]],
    region: [],
    postalCode: []
  });

  constructor(
    protected hospitalService: HospitalService,
    protected addressService: AddressService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hospital }) => {
      this.updateForm(hospital);

      this.addressService
        .query({ filter: 'hospital-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!hospital.address || !hospital.address.id) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(hospital.address.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => {
                this.addresses = concatRes;
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

  getalladresses(): void {
    this.addressService.query().subscribe((res: HttpResponse<IAddress[]>) => (this.addresses = res.body ? res.body : []));
  }

  updateForm(hospital: IHospital): void {
    this.editForm.patchValue({
      id: hospital.id,
      name: hospital.name,
      address: hospital.address,
      headOfSearvice: hospital.headOfSearvice
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hospital = this.createFromForm();
    if (hospital.id !== undefined) {
      this.subscribeToSaveResponse(this.hospitalService.update(hospital));
    } else {
      this.subscribeToSaveResponse(this.hospitalService.create(hospital));
    }
  }

  saveadress(): void {
    this.isSaving = true;
    const address = this.createFromAddr();
    this.subscribeToSaveAddr(this.addressService.create(address));
  }

  private createFromAddr(): IAddress {
    this.newstreetname = this.addaddressform.get(['streetName'])!.value;
    return {
      ...new Address(),
      id: this.addaddressform.get(['id'])!.value,
      streetName: this.addaddressform.get(['streetName'])!.value,
      city: this.addaddressform.get(['city'])!.value,
      region: this.addaddressform.get(['region'])!.value,
      postalCode: this.addaddressform.get(['postalCode'])!.value
    };
  }

  private createFromForm(): IHospital {
    return {
      ...new Hospital(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      headOfSearvice: this.editForm.get(['headOfSearvice'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHospital>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected subscribeToSaveAddr(result: Observable<HttpResponse<IAddress>>): void {
    result.subscribe(
      () => {
        this.isSaving = false;
        this.addedaddress = !this.addedaddress;
        this.getalladresses();
        this.editForm.controls['address'].setValue(this.newstreetname);
      },
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
