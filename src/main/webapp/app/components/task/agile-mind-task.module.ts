import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {AgileMindSharedModule} from 'app/shared/shared.module';
import {DialogModule} from 'primeng/dialog';
import {TASK_ROUTE} from './task.route';
import {TaskCreateComponent} from './create/task-create.component';
import {TaskUpdateComponent} from 'app/components/task/update/task-update.component';

@NgModule({
  imports: [AgileMindSharedModule, RouterModule.forChild(TASK_ROUTE), DialogModule],
  declarations: [
    TaskCreateComponent,
    TaskUpdateComponent
  ],
})
// @ts-ignore
export class AgileMindTaskModule {
}
