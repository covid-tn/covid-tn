import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { CovidTestModule } from '../../../test.module';
import { ServiceRoomComponent } from 'app/entities/service-room/service-room.component';
import { ServiceRoomService } from 'app/entities/service-room/service-room.service';
import { ServiceRoom } from 'app/shared/model/service-room.model';

describe('Component Tests', () => {
  describe('ServiceRoom Management Component', () => {
    let comp: ServiceRoomComponent;
    let fixture: ComponentFixture<ServiceRoomComponent>;
    let service: ServiceRoomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidTestModule],
        declarations: [ServiceRoomComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(ServiceRoomComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceRoomComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceRoomService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ServiceRoom('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.serviceRooms && comp.serviceRooms[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ServiceRoom('123')],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.serviceRooms && comp.serviceRooms[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
