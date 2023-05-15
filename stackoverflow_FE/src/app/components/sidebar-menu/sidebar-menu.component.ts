import { Component } from '@angular/core';
import { MaterialModule } from 'src/app/material/material.module';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { QuestionListComponent } from '../question-list/question-list.component';
import { QuestionComponent } from 'src/app/components/question/question.component';
import { Routes, RouterModule, Router } from '@angular/router';
import { AppService } from 'src/app/services/app.service';
import { CommonModule } from '@angular/common';

@Component({
    standalone: true,
    selector: 'app-sidebar-menu',
    templateUrl: './sidebar-menu.component.html',
    styleUrls: ['./sidebar-menu.component.css'],
    imports: [
      MaterialModule,
      SearchBarComponent,
      QuestionListComponent,
      QuestionComponent,
      RouterModule,
      CommonModule
    ]
})
export class SidebarMenuComponent {

  noContentMainPage: boolean = true;

  constructor(private appService: AppService, private router: Router) {
    if(this.router.url !== '/main') {
      this.noContentMainPage = false;
    } else {
      this.noContentMainPage = true;
    }
  }

  logoutUser() {
    this.appService.logoutUser();
    this.router.navigateByUrl('login');
  }
}
