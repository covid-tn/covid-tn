import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidTestModule } from '../../../test.module';
import { ServiceHospitalDetailComponent } from 'app/entities/service-hospital/service-hospital-detail.component';
import { ServiceHospital } from 'app/shared/model/service-hospital.model';

describe('Component Tests', () => {
  describe('ServiceHospital Management Detail Component', () => {
    let comp: ServiceHospitalDetailComponent;
    let fixture: ComponentFixture<ServiceHospitalDetailComponent>;
    const route = ({ data: of({ serviceHospital: new ServiceHospital('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidTestModule],
        declarations: [ServiceHospitalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceHospitalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceHospitalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serviceHospital on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceHospital).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
