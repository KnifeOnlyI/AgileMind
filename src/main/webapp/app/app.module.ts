import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import './vendor';
import {AgileMindSharedModule} from 'app/shared/shared.module';
import {AgileMindCoreModule} from 'app/core/core.module';
import {AgileMindAppRoutingModule} from './app-routing.module';
import {AgileMindHomeModule} from './home/home.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import {MainComponent} from './layouts/main/main.component';
import {NavbarComponent} from './layouts/navbar/navbar.component';
import {FooterComponent} from './layouts/footer/footer.component';
import {PageRibbonComponent} from './layouts/profiles/page-ribbon.component';
import {ActiveMenuDirective} from './layouts/navbar/active-menu.directive';
import {ErrorComponent} from './layouts/error/error.component';
import {AgileMindProjectModule} from './components/project/agile-mind-project.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AgileMindStoryModule} from 'app/components/story/agile-mind-story.module';
import {AgileMindTaskModule} from 'app/components/task/agile-mind-task.module';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AgileMindSharedModule,
    AgileMindCoreModule,
    AgileMindHomeModule,
    AgileMindProjectModule,
    AgileMindStoryModule,
    AgileMindTaskModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AgileMindAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class AgileMindAppModule {
}
