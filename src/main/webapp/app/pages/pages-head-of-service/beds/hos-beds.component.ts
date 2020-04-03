import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBed } from 'app/shared/model/bed.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { BedStatus } from 'app/shared/model/enumerations/bed-status.model';
import { BedService } from 'app/entities/bed/bed.service';
import { HosBedsDeleteDialogComponent } from 'app/pages/pages-head-of-service/beds/hos-beds-delete-dialog.component';

@Component({
  selector: 'jhi-hos-beds',
  templateUrl: './hos-beds.component.html'
})
export class HosBedsComponent implements OnInit, OnDestroy {
  beds?: IBed[];
  filtredbeds?: IBed[];
  filtredbedsbystatus?: IBed[];
  attachedHospital: IHospital | undefined;

  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  statusfilter: BedStatus = BedStatus.any;

  constructor(
    protected bedService: BedService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected accountService: AccountService
  ) {}

  loadPage(page?: number): void {
    this.getallBeds(page);
  }

  ngOnInit(): void {
    const profile = this.accountService.getCurrentUserProfile();
    this.attachedHospital = profile ? profile.hospital : undefined;
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
    if (this.attachedHospital) {
      this.bedService
        .queryByHospital(
          {
            page: pageToLoad - 1,
            size: this.itemsPerPage,
            sort: this.sort()
          },
          this.attachedHospital.id
        )
        .subscribe(
          (res: HttpResponse<IBed[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
          () => this.onError()
        );
    }
  }

  trackId(index: number, item: IBed): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBeds(): void {
    this.eventSubscriber = this.eventManager.subscribe('bedListModification', () => this.loadPage());
  }

  delete(bed: IBed): void {
    const modalRef = this.modalService.open(HosBedsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
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
    this.router.navigate(['/head-of-services/manage-beds'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });

    this.beds = data ? data : [];
    this.filtredbeds = this.filtredbedsbystatus = this.beds;
  }

  //  To Filter Beds By Status
  FilterByStatus(): void {
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
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
