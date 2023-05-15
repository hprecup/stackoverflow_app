import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginData } from '../common/login-data';
import { map, catchError } from 'rxjs/operators';
import { SessionData } from '../common/session-data';
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  currentUser: SessionData;

  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {
    this.currentUser = JSON.parse(sessionStorage.getItem("currentUser")!);
  }

  authenticate_1(loginData: LoginData): Observable<any> {
    const loginUrl = `${this.baseUrl}/auth/login`;
    return this.httpClient.post<any>(loginUrl, loginData);
  }

  authenticate(loginData: LoginData): Observable<any> {
    const loginUrl = `${this.baseUrl}/auth/login`;
    return this.httpClient.post<any>(loginUrl, loginData).pipe(
      map(((data: any) => {
          let sessionData: SessionData = {
            username: data.username,
            userRoles: data.userRoles,
            accessToken: data.accessToken
          };
          sessionStorage.setItem("currentUser", JSON.stringify(sessionData));
          this.currentUser = sessionData;
          return sessionData;
        }
    )))
  }


  logoutUser() {
    const logoutUrl = `${this.baseUrl}/auth/logout`;
    sessionStorage.clear();
    this.currentUser = null!;
  }

}
