import { Component } from '@angular/core';
import { MaterialModule } from 'src/app/material/material.module';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { QuestionListComponent } from '../question-list/question-list.component';
import { QuestionComponent } from 'src/app/components/question/question.component';
import { Routes, RouterModule } from '@angular/router';

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
      RouterModule
    ]
})
export class SidebarMenuComponent {

}
