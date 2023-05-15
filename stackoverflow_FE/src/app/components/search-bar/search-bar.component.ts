import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/material/material.module';

@Component({
  standalone: true,
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css'],
  imports: [
    MaterialModule,
    RouterModule
  ]
})
export class SearchBarComponent {

  searchString: string = "";

  username: string = JSON.parse(sessionStorage.getItem("currentUser")!).username;

  constructor(private route: ActivatedRoute, private router: Router) {

  }

  searchQuestion(searchedQuestion: string) {
    if(searchedQuestion !== "") {
      this.router.navigate([`main/questions/search/${searchedQuestion}`]);
    } else {
      this.router.navigate(['main/questions']);
    }
  }
}
