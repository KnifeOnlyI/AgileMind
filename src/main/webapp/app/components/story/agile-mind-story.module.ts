import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {AgileMindSharedModule} from 'app/shared/shared.module';
import {DialogModule} from 'primeng/dialog';
import {STORY_ROUTE} from './story.route';
import {StoryCreateComponent} from './create/story-create.component';
import {StoryUpdateComponent} from 'app/components/story/update/story-update.component';
import {AgileMindTaskModule} from 'app/components/task/agile-mind-task.module';

@NgModule({
  imports: [AgileMindSharedModule, RouterModule.forChild(STORY_ROUTE), DialogModule, AgileMindTaskModule],
  declarations: [
    StoryCreateComponent,
    StoryUpdateComponent
  ],
})
// @ts-ignore
export class AgileMindStoryModule {
}
