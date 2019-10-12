import { Injectable } from '@angular/core';
import { Driver } from './driver';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DriverService {
  
  private driversUrl = 'http://localhost:8080/drivers';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) { }

  getDrivers(): Observable<Driver[]> {
    return this.http.get<Driver[]>(this.driversUrl)
    .pipe(
      tap(_ => this.log('fetched drivers')),
      catchError(this.handleError<Driver[]>('getDrivers', []))
    );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
  
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
  
      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);
  
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  getDriver(id: number): Observable<Driver> {
    const url = `${this.driversUrl}/${id}`;
    return this.http.get<Driver>(url).pipe(
      tap(_ => this.log(`fetched driver id=${id}`)),
      catchError(this.handleError<Driver>(`getDriver id=${id}`))
    );
  }

  updateDriver (driver: Driver): Observable<any> {
    return this.http.put(this.driversUrl, driver, this.httpOptions).pipe(
      tap(_ => this.log(`updated driver id=${driver.id}`)),
      catchError(this.handleError<any>('updateDriver'))
    );
  }

  addDriver (driver: Driver): Observable<Driver> {
    return this.http.post<Driver>(this.driversUrl, driver, this.httpOptions).pipe(
      tap((newDriver: Driver) => this.log(`added driver w/ id=${newDriver.id}`)),
      catchError(this.handleError<Driver>('addDriver'))
    );
  }

  deleteDriver (driver: Driver | number): Observable<Driver> {
    const id = typeof driver === 'number' ? driver : driver.id;
    const url = `${this.driversUrl}/${id}`;
  
    return this.http.delete<Driver>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted driver id=${id}`)),
      catchError(this.handleError<Driver>('deleteDriver'))
    );
  }

  private log(message: string) {
    console.log(message);
  }
}


