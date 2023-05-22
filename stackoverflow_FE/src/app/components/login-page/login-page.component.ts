import { Component, OnInit } from '@angular/core';
import { MaterialModule } from 'src/app/material/material.module';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AppService } from 'src/app/services/app.service';
import { LoginData } from 'src/app/common/login-data';
import {MatDialog} from '@angular/material/dialog'
import { BannedUserDialogComponent } from '../banned-user-dialog/banned-user-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
  standalone: true,
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
  imports:[
    MaterialModule,
    RouterModule,
    ReactiveFormsModule,
    CommonModule,
    MatDialogModule
  ]
})
export class LoginPageComponent implements OnInit{
  hidePassword: boolean = true;

  loginFormGroup!: FormGroup;

  constructor(private fb: FormBuilder, private router: Router, private appService: AppService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.hidePassword = true;
    this.loginFormGroup = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    })
  }

  loginUser() {
    if(this.loginFormGroup.valid){
      const username = this.loginFormGroup.get('username')?.value;
      const password = this.loginFormGroup.get('password')?.value;
      this.appService.authenticate(new LoginData(username, password)).subscribe({
        next: (data: any) => {
          if(data.banned === true){
            this.openDialog();
          } else {
            this.router.navigateByUrl('main');
          }
          console.log(data)
        },
        error: (err: Error) => console.log(err.message)
      });
    }
  }


  openDialog(){
    const dialogRef = this.dialog.open(BannedUserDialogComponent)
  }
}
