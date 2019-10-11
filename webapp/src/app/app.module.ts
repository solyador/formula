import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuBarComponent } from './menu-bar/menu-bar.component';
import { DriverListComponent } from './driver-list/driver-list.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TeamListComponent } from './team-list/team-list.component';


@NgModule({
  declarations: [
    AppComponent,
    MenuBarComponent,
    DriverListComponent,
    DashboardComponent,
    TeamListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
