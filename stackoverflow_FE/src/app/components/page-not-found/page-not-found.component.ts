import { Component } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/material/material.module';

@Component({
  standalone: true,
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css'],
  imports: [
    MaterialModule,
    RouterModule
  ]
})
export class PageNotFoundComponent {
  constructor(private activeRoute: ActivatedRoute){

  }
}
