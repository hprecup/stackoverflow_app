import { Component, OnInit } from '@angular/core';
import { MaterialModule } from 'src/app/material/material.module';
import { CommonModule } from '@angular/common';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/common/user';

@Component({
  standalone: true,
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  imports: [
    MaterialModule,
    CommonModule
  ]
})
export class UserListComponent implements OnInit {

  constructor(private route: ActivatedRoute, private userService: UserService) {

  }

  users: User[] = [];

  ngOnInit(): void {
    this.route.paramMap.subscribe(() =>
      this.fetchData()
    );
  }

  fetchData() {
    console.log("users")
    this.userService.getUserList().subscribe(
      users => this.users = users
    );
  }

}
