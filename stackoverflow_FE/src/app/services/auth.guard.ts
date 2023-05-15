import {inject} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from './app.service';


export const authGuard = () => {
  const appService = inject(AppService);
  const router = inject(Router);

  if(sessionStorage.getItem("currentUser") !== null) {
    return true;
  }

  // if (appService.currentUser !== null) {
  //   return true;
  // }

  // Redirect to the login page
  return router.parseUrl('/login');
};

export const loginGuard = () => {
  const appService = inject(AppService);
  const router = inject(Router);

  if (appService.currentUser === null) {
    return true;
  }

  // Redirect to the main page
  return router.parseUrl('/main');
};

