import { Component } from '@angular/core';

@Component({
  selector: 'jhi-navbar-doctor',
  templateUrl: './navbar-doctor.component.html'
})
export class NavbarDoctorComponent {
  isNavbarCollapsed = true;

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }
}
