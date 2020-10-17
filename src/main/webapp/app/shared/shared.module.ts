import {NgModule} from '@angular/core';
import {AgileMindSharedLibsModule} from './shared-libs.module';
import {FindLanguageFromKeyPipe} from './language/find-language-from-key.pipe';
import {AlertComponent} from './alert/alert.component';
import {AlertErrorComponent} from './alert/alert-error.component';
import {LoginModalComponent} from './login/login.component';
import {HasAnyAuthorityDirective} from './auth/has-any-authority.directive';
import {MessageListComponent} from './components/message-list/message-list.component';
import {MessageComponent} from 'app/shared/components/message/message.component';
import {ButtonSaveComponent} from 'app/shared/components/buttons/button-save.component';
import {ButtonCancelComponent} from 'app/shared/components/buttons/button-cancel.component';
import {ButtonDeleteComponent} from 'app/shared/components/buttons/button-delete.component';

@NgModule({
  imports: [AgileMindSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    MessageListComponent,
    MessageComponent,
    ButtonSaveComponent,
    ButtonCancelComponent,
    ButtonDeleteComponent,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    AgileMindSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    MessageListComponent,
    MessageComponent,
    ButtonSaveComponent,
    ButtonCancelComponent,
    ButtonDeleteComponent,
  ],
})
export class AgileMindSharedModule {}
