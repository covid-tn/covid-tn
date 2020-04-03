import { Component, OnInit } from '@angular/core';
import { ProfileService } from 'app/layouts/profiles/profile.service';

@Component({
  selector: 'jhi-navbar-admin',
  templateUrl: './navbar-admin.component.html'
})
export class NavbarAdminComponent implements OnInit {
  isNavbarCollapsed = true;
  swaggerEnabled?: boolean;
  inProduction?: boolean;

  constructor(private profileService: ProfileService) {}

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }
}
