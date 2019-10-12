import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DriverListComponent } from './driver-list/driver-list.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TeamListComponent } from './team-list/team-list.component';
import { DriverDetailsComponent } from './driver-details/driver-details.component';


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'drivers', component: DriverListComponent },
  { path: 'driver/:id', component: DriverDetailsComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'teams', component: TeamListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
