import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {MatDialogModule} from '@angular/material/dialog';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';

import {authInterceptorProviders} from './_helpers/auth.interceptor';
import {CarListComponent} from './car-list/car-list.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ServicepointComponent} from './servicepoint/servicepoint.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatOptionModule} from "@angular/material/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {CurrencySelectionComponent} from "./_shared/currencySelection/currency-selection.component";
import {CarBookingListComponent} from "./car-booking-list/car-booking-list.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    ServicepointComponent,
    CarListComponent,
    CarBookingListComponent,
    CurrencySelectionComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    FontAwesomeModule,
    MatDialogModule,
    BrowserAnimationsModule,
    MatAutocompleteModule,
    MatOptionModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
