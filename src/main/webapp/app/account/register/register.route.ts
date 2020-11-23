import {Route} from '@angular/router';

import {RegisterComponent} from './register.component';

// Not actually used
export const registerRoute: Route = {
  path: 'register',
  component: RegisterComponent,
  data: {
    authorities: [],
    pageTitle: 'register.title',
  },
};
