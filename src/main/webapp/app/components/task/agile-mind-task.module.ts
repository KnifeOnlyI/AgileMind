import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {AgileMindSharedModule} from 'app/shared/shared.module';
import {DialogModule} from 'primeng/dialog';
import {TASK_ROUTE} from './task.route';
import {TaskCreateComponent} from './create/task-create.component';
import {TaskUpdateComponent} from 'app/components/task/update/task-update.component';
import {TaskListComponent} from 'app/components/task/list/task-list.component';
import {DragDropModule} from '@angular/cdk/drag-drop';

@NgModule({
  imports: [AgileMindSharedModule, RouterModule.forChild(TASK_ROUTE), DialogModule, DragDropModule],
  declarations: [
    TaskCreateComponent,
    TaskUpdateComponent,
    TaskListComponent,
  ],
  exports: [
    TaskListComponent
  ]
})
// @ts-ignore
export class AgileMindTaskModule {
}
