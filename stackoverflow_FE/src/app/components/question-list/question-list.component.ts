import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormBuilder, ReactiveFormsModule, Validators, ValidatorFn } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router, RouterEvent } from '@angular/router';
import { MaterialModule } from 'src/app/material/material.module';
import { RouterModule } from '@angular/router';
import { Question } from 'src/app/common/question';
import { QuestionService } from 'src/app/services/question.service';
import { Tag } from 'src/app/common/tag';
import { InsertQuestion } from 'src/app/common/insert-question';
import { User } from 'src/app/common/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  standalone: true,
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css'],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    RouterModule
  ]
})
export class QuestionListComponent implements OnInit{

  onlyMyQuestions = new FormControl(false);

  enableQuestionInsert: boolean = false;

  enableInsertTag: boolean = false;

  allQuestions: Question[] = [];

  displayedQuestions: Question[] = [];

  filterUsernames: string[] = ["All"];

  selectedUsername: string = this.filterUsernames[0];

  tags: Tag[] = [];

  filterTags: string[] = ["All"];

  selectedTag: string = this.filterTags[0];

  fileName = '';

  newTag = new FormControl("", [Validators.pattern('\\S+')]);

  questionInsertFormGroup: FormGroup =  new FormGroup({
    title: new FormControl('', [Validators.required]),
    text: new FormControl('', [Validators.required]),
    tags: new FormControl([], [Validators.required])
  });

  constructor(private route: ActivatedRoute, private questionService: QuestionService, private userService: UserService, private router: Router){
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() =>
      this.fetchData()
    );
  }

  fetchData() {
    const searchString = this.route.snapshot.paramMap.get("searchedQuestion");
    if(searchString !== null) {
      this.questionService.searchQuestion(searchString).subscribe(
        questions => {
          this.allQuestions = questions
          this.displayedQuestions = questions
        }
      );
    } else {
      this.questionService.getQuestionList().subscribe(
        questions => {
          this.allQuestions = questions
          this.displayedQuestions = questions
        }
      );
    }

    this.questionService.getTags().subscribe(
      tags => {
        this.tags = tags
        for(let tag of tags){
          this.filterTags.push(tag.tagName);
        }
      }
    );

    this.userService.getUserList().subscribe(
      users => {
        for(let user of users){
          this.filterUsernames.push(user.username);
        }
      }
    );
  }

  toggleQuestionInsert() {
    this.enableQuestionInsert = !this.enableQuestionInsert;
    this.enableInsertTag = false;
    this.newTag.setValue("");
  }

  onFileSelected(event: any) {
    console.log(event);

    const file:File = event.target.files[0];

    if (file) {

        this.fileName = file.name;
        console.log(this.fileName);
        // const formData = new FormData();

        // formData.append("thumbnail", file);

        // const upload$ = this.http.post("/api/thumbnail-upload", formData);

        // upload$.subscribe();
    }
  }

  toggleTagInsert() {
    this.enableInsertTag = !this.enableInsertTag;
    this.newTag.setValue("");
  }

  addTag() {
    if(this.newTag.valid){
      let newTagName = this.newTag.value!;
      let valid = this.validateTagName(newTagName);
      if(valid) {
        this.questionService.addTag(newTagName).subscribe(
          tag => {
            this.tags.push(tag)
            let selectedTagsFC = this.questionInsertFormGroup.get('tags')?.value;
            selectedTagsFC.push(tag.id);
            this.questionInsertFormGroup.get('tags')?.setValue(selectedTagsFC);
            this.filterTags.push(tag.tagName);
          }
        );
        this.toggleTagInsert();
      }
    }
  }

  validateTagName(newTag: string): boolean {
    if(newTag === '')
      return false;
    for(var tag of this.tags){
      if(tag.tagName === newTag)
        return false;
    }

    return true;
  }

  insertQuestion(){
    if(this.questionInsertFormGroup.valid && this.fileName!=='') {
      let questionTitle = this.questionInsertFormGroup.get('title')?.value;
      let questionText = this.questionInsertFormGroup.get('text')?.value;
      let selectedTags = this.questionInsertFormGroup.get('tags')?.value;
       const newQuestion = new InsertQuestion(1, questionTitle, questionText,
                          "assets/images/demo_photo.jpg", selectedTags);
      this.questionService.insertQuestion(newQuestion).subscribe(
        question => {
          this.displayedQuestions.unshift(question)
          this.toggleQuestionInsert()
        }
      );
    }
  }

  filterPersonalQuestions(filteredQuestions: Question[]): Question[] {
    if(this.onlyMyQuestions.value === true) {
      let currentUsername = JSON.parse(sessionStorage.getItem("currentUser")!).username;
      let newQuestions : Question[] = [];
      for(let question of filteredQuestions) {
        if(question.user.username === currentUsername){
          newQuestions.push(question);
        }
      }
      return newQuestions;
    }
    return filteredQuestions;
  }

  filterQuestionsByTag(filteredQuestions: Question[], filterTagName: string): Question[] {
    if(filterTagName !== "All"){
      let newQuestions: Question[] = [];
      for(let question of filteredQuestions){
        if(question.tagNames!== null && question.tagNames.indexOf(filterTagName) !== -1) {
          newQuestions.push(question);
        }
      }
      return newQuestions;
    }
    return filteredQuestions;
  }

  filterQuestionsByUser(filteredQuestions: Question[], filterUsername: string): Question[] {
    if(this.onlyMyQuestions.value !== true && filterUsername !== "All"){
      let newQuestions: Question[] = [];
      for(let question of filteredQuestions){
        if(question.user.username === filterUsername){
          newQuestions.push(question);
        }
      }
      return newQuestions;
    }
    return filteredQuestions;
  }

  filterQuestions(filterTagName: string, filterUsername: string) {
    let filteredQuestions: Question[] = this.allQuestions;

    filteredQuestions = this.filterPersonalQuestions(filteredQuestions);
    filteredQuestions = this.filterQuestionsByTag(filteredQuestions, filterTagName);
    filteredQuestions = this.filterQuestionsByUser(filteredQuestions, filterUsername);

    this.displayedQuestions = filteredQuestions;
  }

}

