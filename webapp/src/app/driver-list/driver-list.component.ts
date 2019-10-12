import { Component, OnInit } from '@angular/core';
import { DriverService } from '../driver.service';
import { Driver } from '../driver';

@Component({
  selector: 'app-driver-list',
  templateUrl: './driver-list.component.html',
  styleUrls: ['./driver-list.component.scss']
})
export class DriverListComponent implements OnInit {

  columns: any[];
  drivers: Driver[];
  constructor(private driverService: DriverService) { }

  ngOnInit() {
    this.columns = [
      { field: 'id', header: 'Id' },
      {field: 'firstName', header: 'FirstName' },
      { field: 'lastName', header: 'LastName' },
      { field: 'team', header: 'Team' }
  ];
    this.getDrivers();
  }

  getDrivers(): void {
    this.driverService.getDrivers().subscribe( drivers => this.drivers = drivers)
  }

}
