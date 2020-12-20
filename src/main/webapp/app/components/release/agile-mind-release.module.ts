import {NgModule} from '@angular/core';

import {AgileMindSharedModule} from 'app/shared/shared.module';
import {DialogModule} from 'primeng/dialog';
import {ReleaseListComponent} from './list/release-list.component';
import {RouterModule} from '@angular/router';
import {ReleaseUpdateComponent} from 'app/components/release/update/release-update.component';
import {RELEASE_ROUTE} from 'app/components/release/release.route';
import {ReleaseCreateComponent} from 'app/components/release/create/release-create.component';

@NgModule({
  imports: [AgileMindSharedModule, RouterModule.forChild(RELEASE_ROUTE), DialogModule, RouterModule],
  declarations: [
    ReleaseListComponent,
    ReleaseCreateComponent,
    ReleaseUpdateComponent,
  ],
  exports: [
    ReleaseListComponent,
    ReleaseCreateComponent,
    ReleaseUpdateComponent,
  ]
})
// @ts-ignore
export class AgileMindReleaseModule {
}
