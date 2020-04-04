import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidTestModule } from '../../../test.module';
import { ServiceRoomDetailComponent } from 'app/entities/service-room/service-room-detail.component';
import { ServiceRoom } from 'app/shared/model/service-room.model';

describe('Component Tests', () => {
  describe('ServiceRoom Management Detail Component', () => {
    let comp: ServiceRoomDetailComponent;
    let fixture: ComponentFixture<ServiceRoomDetailComponent>;
    const route = ({ data: of({ serviceRoom: new ServiceRoom('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidTestModule],
        declarations: [ServiceRoomDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceRoomDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceRoomDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serviceRoom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceRoom).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
