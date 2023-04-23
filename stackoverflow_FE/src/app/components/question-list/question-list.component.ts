import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgModel, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router, RouterEvent } from '@angular/router';
import { MaterialModule } from 'src/app/material/material.module';
import { RouterModule } from '@angular/router';
import { Question } from 'src/app/common/question';
import { QuestionService } from 'src/app/services/question.service';
import { Tag } from 'src/app/common/tag';
import { InsertQuestion } from 'src/app/common/insert-question';

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

  disableSelect = new FormControl(false);

  enableQuestionInsert: boolean = false;

  enableInsertTag: boolean = false;

  questions: Question[] = [];

  tags: Tag[] = [];

  filterTags: string[] = [""];

  tagsFC = new FormControl('');

  selectedTags : any[] = [];

  validTag: boolean = true;

  validNewQuestion: boolean = true;

  constructor(private route: ActivatedRoute, private questionService: QuestionService, private router: Router){
    // this.route.paramMap.subscribe((data) => {
    //   this.fetchData()
    //   console.log(data.has('id'))
    // }
    // );
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() =>
      this.fetchData()
    );
  }

  fetchData() {
    console.log("se fetchuieste");
    const searchString = this.route.snapshot.paramMap.get("searchedQuestion");
    if(searchString !== null) {
      this.questionService.searchQuestion(searchString).subscribe(
        questions => this.questions = questions
      );
    } else {
      this.questionService.getQuestionList().subscribe(
        questions => {
          console.log(questions)
          this.questions = questions
        }
      );
    }

    this.questionService.getTags().subscribe(
      tags => {
        this.tags = tags
        this.setFilterTags(tags);
      }
    );

    for(let tag of this.tags){
      this.filterTags.push(tag.tagName);
    }
  }

  setFilterTags(tags: Tag[]) {
    for(let tag of this.tags){
      this.filterTags.push(tag.tagName);
    }
  }

  toggleQuestionInsert() {
    this.enableQuestionInsert = !this.enableQuestionInsert;
    //console.log(this.tagsFC.value)
    //console.log(this.selectedTags);
    this.validTag = true;
    this.selectedTags = [];
    //this.tagsFC.reset()
    //console.log(this.tagsFC.value)
  }

  toggleTagInsert() {
    this.enableInsertTag = !this.enableInsertTag;
  }

  addTag(newTag: string) {
    let valid = this.validateTagName(newTag);
    if(valid) {
      this.questionService.addTag(newTag).subscribe(
        tag => {
          this.tags.push(tag)
          this.selectedTags.push(tag.id);
        }
      );
      this.toggleTagInsert();
    } else {
      this.validTag = false;
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

  insertQuestion(questionTitle: string, questionText: string){
    if(questionTitle.length === 0 || questionText.length === 0|| this.selectedTags.length===0){
      this.validNewQuestion = false;
    }
    else{
      const newQuestion = new InsertQuestion(1, questionTitle, questionText,
                          "assets/images/demo_photo.jpg", this.selectedTags);
      console.log(newQuestion)
      this.questionService.insertQuestion(newQuestion).subscribe(
        question => {
          this.questions.unshift(question)
          this.toggleQuestionInsert()
        }
      );
    }
  }

  filterByTag(obj: any) {
    console.log(obj.value)
    //fac 2-3 functii diferite si cand se schimba una iterez pe questionurile initiale si aplic fiecarui element toate functiile
    // in functia mea de filter by tag, verific daca tagul este "" si daca e diferit de null, doar returnez true sau false daca elementul
    // parcurs are tagul egal
    // ca si cum as avea o list de filters si parcurgant elementele, aplic filterele
    // daca fac cu pagination => din BE
  }

}

