import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { User } from '../common/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/user';

  constructor(private httpClient: HttpClient) {

  }

  getUserList(): Observable<User[]> {
    const allUsers = `${this.baseUrl}/getAll`;
    return this.httpClient.get<User[]>(allUsers);
  }

  banUser(userId: number) {
    const banUrl = `${this.baseUrl}/ban/${userId}`;
    this.httpClient.put(banUrl, null).subscribe(() => (""));
  }

  unbanUser(userId: number) {
    const unbanUrl = `${this.baseUrl}/unban/${userId}`;
    this.httpClient.put(unbanUrl, null).subscribe(() => (""));
  }

  getLoggedUser(): Observable<User> {
    // const loggedUrl = `${this.baseUrl}/getLoggedUser`;
    // //return this.httpClient.get<User>(loggedUrl);
    // try {
    //   await this.httpClient.get<User>(loggedUrl).toPromise();
    // } catch (error) {
    //   console.error('Failed to delete question:', error);
    //   throw error;
    // }
    const loggedUrl = `${this.baseUrl}/getLoggedUser`;
    //return this.httpClient.get<User>(loggedUrl).pipe(map(response => response));
    return this.httpClient.get<User>(loggedUrl);
  }

}
