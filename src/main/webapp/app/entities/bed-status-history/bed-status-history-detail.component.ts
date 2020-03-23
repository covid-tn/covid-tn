import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBedStatusHistory } from 'app/shared/model/bed-status-history.model';

@Component({
  selector: 'jhi-bed-status-history-detail',
  templateUrl: './bed-status-history-detail.component.html'
})
export class BedStatusHistoryDetailComponent implements OnInit {
  bedStatusHistory: IBedStatusHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedStatusHistory }) => {
      this.bedStatusHistory = bedStatusHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
