import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { SessionData } from '../common/session-data';
import { AppService } from './app.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{

  constructor(public appService: AppService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(this.appService.currentUser !== null) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.appService.currentUser.accessToken}`
        }
      });
    }
    return next.handle(req).pipe(
      catchError((err) => {
        if (err.status === 401) {
          this.appService.logoutUser();
        }
        const error = err.error.message || err.statusText;
        return throwError(error);
      })
    );
    // throw new Error('Method not implemented.');
  }
}
