import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidTestModule } from '../../../test.module';
import { BedStatusHistoryDetailComponent } from 'app/entities/bed-status-history/bed-status-history-detail.component';
import { BedStatusHistory } from 'app/shared/model/bed-status-history.model';

describe('Component Tests', () => {
  describe('BedStatusHistory Management Detail Component', () => {
    let comp: BedStatusHistoryDetailComponent;
    let fixture: ComponentFixture<BedStatusHistoryDetailComponent>;
    const route = ({ data: of({ bedStatusHistory: new BedStatusHistory('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidTestModule],
        declarations: [BedStatusHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BedStatusHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BedStatusHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bedStatusHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bedStatusHistory).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
