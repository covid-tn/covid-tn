import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CovidSharedModule } from 'app/shared/shared.module';
import { CovidCoreModule } from 'app/core/core.module';
import { CovidAppRoutingModule } from './app-routing.module';
import { CovidHomeModule } from './home/home.module';
import { CovidEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { NavbarModule } from 'app/layouts/navbar/navbar.module';

@NgModule({
  imports: [
    BrowserModule,
    CovidSharedModule,
    CovidCoreModule,
    CovidHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CovidEntityModule,
    CovidAppRoutingModule,
    NavbarModule
  ],
  declarations: [MainComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class CovidAppModule {}
