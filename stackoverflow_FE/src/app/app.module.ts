import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SidebarMenuComponent } from './components/sidebar-menu/sidebar-menu.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { Routes, RouterModule, ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { QuestionListComponent } from './components/question-list/question-list.component';
import { QuestionComponent } from './components/question/question.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { authGuard, loginGuard } from './services/auth.guard';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';

const routes: Routes = [
  {path: 'login', component: LoginPageComponent, canActivate: [loginGuard]},
  {path: 'main', component: SidebarMenuComponent, canActivate: [authGuard],
    children: [
      {path: 'questions', component: QuestionListComponent},
      {path: 'questions/:id',component: QuestionComponent, pathMatch: 'full'},
      {path: 'questions/search/:searchedQuestion',component: QuestionListComponent, pathMatch: 'full'},
      {path: 'users', component: UserListComponent},
    ]
  },
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: '**', component: PageNotFoundComponent, canActivate: [authGuard]},
  {path: '**', redirectTo: '/login', pathMatch: 'full'},
];


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    SidebarMenuComponent,
    SearchBarComponent,
    MatSidenavModule,
    QuestionListComponent,
    QuestionComponent,
    RouterModule.forRoot(routes, {
      onSameUrlNavigation: 'reload'
    }),
    LoginPageComponent,
    UserListComponent,
    PageNotFoundComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private route: ActivatedRoute, private router: Router) {
    this.reloadComponent()
  }

  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    //this.router.navigate(['/same-route']);
  }
}
