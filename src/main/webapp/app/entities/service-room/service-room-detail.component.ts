import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceRoom } from 'app/shared/model/service-room.model';

@Component({
  selector: 'jhi-service-room-detail',
  templateUrl: './service-room-detail.component.html'
})
export class ServiceRoomDetailComponent implements OnInit {
  serviceRoom: IServiceRoom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceRoom }) => {
      this.serviceRoom = serviceRoom;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
