import { Component } from '@angular/core';

@Component({
  selector: 'jhi-navbar-head-of-service',
  templateUrl: './navbar-head-of-service.component.html'
})
export class NavbarHeadOfServiceComponent {
  isNavbarCollapsed = true;

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }
}
