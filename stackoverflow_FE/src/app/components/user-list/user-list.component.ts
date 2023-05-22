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

  userIsAdmin: boolean = JSON.parse(sessionStorage.getItem("currentUser")!).userRoles.includes('MODERATOR');

  constructor(private route: ActivatedRoute, private userService: UserService) {

  }

  users: User[] = [];

  ngOnInit(): void {
    this.route.paramMap.subscribe(() =>
      this.fetchData()
    );
  }

  fetchData() {
    this.userService.getUserList().subscribe(
      users => {
        console.log(users)
        this.users = users
      }
    );
  }

  banUser(userId: number) {
    this.userService.banUser(userId);
    this.changeUserStatus(userId);
  }

  unbanUser(userId: number) {
    this.userService.unbanUser(userId);
    this.changeUserStatus(userId);
  }

  changeUserStatus(userId: number){
    this.users = this.users.map(user => {
      if(user.id === userId) {
        user.banned = !user.banned;
      }
      return user;
    });
  }

}
