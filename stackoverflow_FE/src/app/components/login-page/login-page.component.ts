import { Component, OnInit } from '@angular/core';
import { MaterialModule } from 'src/app/material/material.module';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
  imports:[
    MaterialModule,
    RouterModule
  ]
})
export class LoginPageComponent implements OnInit{
  hidePassword: boolean = true;

  // FAC CU FORM CONTROL PENTRU VALIDATORI LA FIECARE CAMP SI CU ERROR MESSAGES

  ngOnInit(): void {
    this.hidePassword = true;
  }

}
