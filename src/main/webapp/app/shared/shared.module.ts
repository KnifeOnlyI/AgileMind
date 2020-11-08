import {NgModule} from '@angular/core';
import {AgileMindSharedLibsModule} from './shared-libs.module';
import {FindLanguageFromKeyPipe} from './language/find-language-from-key.pipe';
import {AlertComponent} from './alert/alert.component';
import {AlertErrorComponent} from './alert/alert-error.component';
import {LoginModalComponent} from './login/login.component';
import {HasAnyAuthorityDirective} from './auth/has-any-authority.directive';
import {ButtonSaveComponent} from 'app/shared/components/buttons/button-save.component';
import {ButtonCancelComponent} from 'app/shared/components/buttons/button-cancel.component';
import {ButtonDeleteComponent} from 'app/shared/components/buttons/button-delete.component';
import {StoryStatusSelectComponent} from 'app/shared/components/fields/story-status-select/story-status-select.component';
import {UserSelectComponent} from 'app/shared/components/fields/user-select/user-select.component';
import {StoryStatusComponent} from 'app/shared/components/fields/story-status/story-status.component';
import {UserComponent} from 'app/shared/components/fields/user/user.component';
import {ConfirmDialogComponent} from 'app/shared/components/dialogs/confirm/confirm-dialog.component';

@NgModule({
  imports: [AgileMindSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ButtonSaveComponent,
    ButtonCancelComponent,
    ButtonDeleteComponent,
    StoryStatusSelectComponent,
    UserSelectComponent,
    StoryStatusComponent,
    UserComponent,
    ConfirmDialogComponent,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    AgileMindSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ButtonSaveComponent,
    ButtonCancelComponent,
    ButtonDeleteComponent,
    StoryStatusSelectComponent,
    UserSelectComponent,
    StoryStatusComponent,
    UserComponent,
    ConfirmDialogComponent,
  ],
})
export class AgileMindSharedModule {
}
