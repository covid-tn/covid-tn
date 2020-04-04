import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidTestModule } from '../../../test.module';
import { ServiceHospitalUpdateComponent } from 'app/entities/service-hospital/service-hospital-update.component';
import { ServiceHospitalService } from 'app/entities/service-hospital/service-hospital.service';
import { ServiceHospital } from 'app/shared/model/service-hospital.model';

describe('Component Tests', () => {
  describe('ServiceHospital Management Update Component', () => {
    let comp: ServiceHospitalUpdateComponent;
    let fixture: ComponentFixture<ServiceHospitalUpdateComponent>;
    let service: ServiceHospitalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidTestModule],
        declarations: [ServiceHospitalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceHospitalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceHospitalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceHospitalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceHospital('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceHospital();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
