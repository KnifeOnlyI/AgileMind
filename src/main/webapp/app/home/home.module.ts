import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {AgileMindSharedModule} from 'app/shared/shared.module';
import {HOME_ROUTE} from './home.route';
import {HomeComponent} from './home.component';
import {AgileMindProjectModule} from 'app/components/project/agile-mind-project.module';

@NgModule({
  imports: [AgileMindSharedModule, RouterModule.forChild([HOME_ROUTE]), AgileMindProjectModule],
  declarations: [HomeComponent],
})
export class AgileMindHomeModule {
}
