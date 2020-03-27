import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBed } from 'app/shared/model/bed.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BedService } from './bed.service';
import { BedDeleteDialogComponent } from './bed-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { HospitalService } from '../hospital/hospital.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { BedStatus } from 'app/shared/model/enumerations/bed-status.model';

@Component({
  selector: 'jhi-bed',
  templateUrl: './bed.component.html'
})
export class BedComponent implements OnInit, OnDestroy {
  hospitals?: IHospital[];
  beds?: IBed[];
  filtredbeds?: IBed[];
  filtredbedsbystatus?: IBed[];

  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  statusfilter: BedStatus = BedStatus.any;
  hospitalfilter: String = 'any';

  constructor(
    protected bedService: BedService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected accountService: AccountService,
    protected hospitalService: HospitalService
  ) {}

  loadPage(page?: number): void {
    this.getallBeds(page);

    this.hospitalService.query().subscribe(
      (res: HttpResponse<IHospital[]>) => (this.hospitals = res.body ? res.body : []),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInBeds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  getallBeds(page?: number): void {
    const pageToLoad: number = page ? page : this.page;
    this.bedService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IBed[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  trackId(index: number, item: IBed): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBeds(): void {
    this.eventSubscriber = this.eventManager.subscribe('bedListModification', () => this.loadPage());
  }

  delete(bed: IBed): void {
    const modalRef = this.modalService.open(BedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bed = bed;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IBed[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/bed'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });

    // TODO : Remove me as sooooooooooooon as possible !!!!!
    if (this.accountService.hasAnyAuthority('ROLE_ADMIN')) {
      this.beds = data ? data : [];
      this.filtredbeds = this.filtredbedsbystatus = this.beds;
    } else {
      this.beds = data
        ? data.filter(bed => {
            if (bed && bed.hospital) {
              return bed.hospital.name === 'Hopital test';
            }
            return false;
          })
        : [];
      this.filtredbeds = this.filtredbedsbystatus = this.beds;
    }
  }

  //  To Filter Beds By Status
  protected FilterByStatus(): void {
    //  Filter
    if (this.statusfilter === 'any') {
      this.filtredbedsbystatus = this.beds;
      this.filtredbeds = this.beds;
    } else {
      if (this.beds !== undefined) {
        this.filtredbedsbystatus = this.beds.filter(bed => {
          return bed.status === this.statusfilter;
        });

        this.filtredbeds = [...this.filtredbedsbystatus];
      }
    }
    this.FilterByHospital();
  }

  //  To Filter Beds By Hospital
  protected FilterByHospital(): void {
    //  Filter
    if (this.hospitalfilter === 'any') {
      this.filtredbeds = this.filtredbedsbystatus;
    } else {
      if (this.filtredbedsbystatus !== undefined) {
        this.filtredbeds = this.filtredbedsbystatus.filter(bed => {
          if (bed.hospital !== undefined) return bed.hospital.name === this.hospitalfilter;
          else return null;
        });

        this.filtredbeds = [...this.filtredbeds];
      }
    }
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
