import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidTestModule } from '../../../test.module';
import { BedStatusHistoryUpdateComponent } from 'app/entities/bed-status-history/bed-status-history-update.component';
import { BedStatusHistoryService } from 'app/entities/bed-status-history/bed-status-history.service';
import { BedStatusHistory } from 'app/shared/model/bed-status-history.model';

describe('Component Tests', () => {
  describe('BedStatusHistory Management Update Component', () => {
    let comp: BedStatusHistoryUpdateComponent;
    let fixture: ComponentFixture<BedStatusHistoryUpdateComponent>;
    let service: BedStatusHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidTestModule],
        declarations: [BedStatusHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BedStatusHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BedStatusHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BedStatusHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BedStatusHistory('123');
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
        const entity = new BedStatusHistory();
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
