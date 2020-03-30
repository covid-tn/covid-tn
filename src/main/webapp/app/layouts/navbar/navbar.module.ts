import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidSharedModule } from 'app/shared/shared.module';
import { NavbarComponent } from './navbar.component';
import { NavbarAdminComponent } from './navbar-admin/navbar-admin.component';
import { NavbarHeadOfServiceComponent } from './navbar-head-of-service/navbar-head-of-service.component';
import { NavbarDoctorComponent } from './navbar-doctor/navbar-doctor.component';

@NgModule({
  imports: [CovidSharedModule, RouterModule],
  declarations: [NavbarComponent, NavbarAdminComponent, NavbarHeadOfServiceComponent, NavbarDoctorComponent],
  exports: [NavbarComponent, NavbarAdminComponent, NavbarHeadOfServiceComponent, NavbarDoctorComponent]
})
export class NavbarModule {}
