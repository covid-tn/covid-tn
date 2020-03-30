import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'head-of-services',
        loadChildren: () => import('./pages-head-of-service/pages-head-of-service.module').then(value => value.PagesHeadOfServiceModule)
      }
    ])
  ]
})
export class PagesModule {}
