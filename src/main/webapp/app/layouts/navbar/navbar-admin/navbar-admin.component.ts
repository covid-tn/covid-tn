import { Component } from '@angular/core';

@Component({
  selector: 'jhi-navbar-admin',
  templateUrl: './navbar-admin.component.html'
})
export class NavbarAdminComponent {
  isNavbarCollapsed = true;

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }
}
