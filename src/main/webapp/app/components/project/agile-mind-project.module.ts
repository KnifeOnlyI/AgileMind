import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {PROJECT_ROUTE} from './project.route';
import {AgileMindSharedModule} from 'app/shared/shared.module';
import {ProjectListComponent} from './list/project-list.component';
import {DialogModule} from 'primeng/dialog';
import {ProjectViewComponent} from 'app/components/project/view/project-view.component';
import {ProjectCreateComponent} from 'app/components/project/create/project-create.component';
import {ProjectUpdateComponent} from 'app/components/project/update/project-update.component';

@NgModule({
  imports: [AgileMindSharedModule, RouterModule.forChild(PROJECT_ROUTE), DialogModule],
  declarations: [
    ProjectListComponent,
    ProjectCreateComponent,
    ProjectUpdateComponent,
    ProjectViewComponent,
  ],
  exports: [
    ProjectListComponent
  ]
})
// @ts-ignore
export class AgileMindProjectModule {
}
