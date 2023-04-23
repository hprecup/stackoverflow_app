import { Component, OnInit, importProvidersFrom } from '@angular/core';
import { MaterialModule } from 'src/app/material/material.module';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Question } from '../../common/question';
import { QuestionService } from '../../services/question.service';
import { User } from '../../common/user';
import { Answer } from '../../common/answer';
import { InsertAnswer } from '../../common/insert-answer';
import { AnswerService } from '../../services/answer.service';

@Component({
  standalone: true,
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css'],
  imports:[
    MaterialModule,
    CommonModule,
    RouterModule
  ]
})
export class QuestionComponent implements OnInit{

  //questionContent: string = "În conformitate cu prevederile legale, cu regulamentele și instrucțiunile în vigoare, revizorul școlar efectua inspecții și anchete, conducea conferința anuală a învățătorilor programată vara, lua parte la diferite manifestări culturale, sărbători legale și naționale etc. Revizorul I.L. Caragiale a procedat în activitatea sa în funcție de condițiile existente în județul Argeș.În perioada revizoratului școlar, dramaturgul Ion Luca Caragiale a locuit în Pitești, în cea mai mare parte a timpului, la hotelul-restaurant „Dacia” din strada Șerban-Vodă, local central de local de categoria I, cu saloane și separeuri în restaurant, cu sală de spectacole, club, bar, cafenea, cofetărie, grădină de vară cu scenă și ring de dans. Localul se situa la nivelul celor similare din București, Hotelul „Dacia” era renumit și prin spectacolele de varietăți susținute de actori profesioniști români și străini.";

  // SA FAC CU FORM CONTROL FIELDURILE EVENTUAL, FAC UN FORM CONTROL AICI, IN HTML PUN NUMELE LA FIECARE FORM FIELD SI IN CONSTRUCTOR
  // INITIALIZEZ FIECARE FIELD SI PUN VALIDATORI SPECIFICI
  answerDate: Date = new Date();

  question: Question = new Question(0, "Not an existing question", "", "", new Date(), [], new User(0, "", "", "", "", 0), 0);

  questionText: string = "";

  answers: Answer[] = [];

  disableQuestionEditing: boolean = true;
  disableAnswerEditing: boolean = true;

  enableAnswerInsert: boolean = false;

  constructor(public activatedRoute: ActivatedRoute, private questionService: QuestionService,
    private answerService: AnswerService, private router: Router) {
  }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    const questionId: number = +this.activatedRoute.snapshot.paramMap.get('id')!;
    this.questionService.getQuestion(questionId).subscribe(
      question => {
        this.question = question
        this.questionText = question.text
      }
    );

    this.questionService.getQuestionAnswers(questionId).subscribe(
      answers => {
        this.answers = answers
      }
    );
  }

  toggleQuestionEdit() {
    this.disableQuestionEditing = !this.disableQuestionEditing;
    this.questionText = this.question.text;
    //console.log(this.question.text);
  }

  toggleAnswerEdit() {
    this.disableAnswerEditing = !this.disableAnswerEditing;
  }

  toggleAnswerInsert() {
    this.enableAnswerInsert = !this.enableAnswerInsert;
  }

  editQuestion(questionText: string) {
    this.questionService.editQuestion(this.question.id, questionText).subscribe(
      question => {
        this.question = question
        this.questionText = question.text
        console.log(question)
      }
    )
    this.toggleQuestionEdit();
  }

  async deleteQuestion() {
    // daca iar apare un question care e deleted => inseamna ca nu se asteapta dupa delete ul din DB
    // in question-list se face de fiecare data fetch la date, deci nu e problema ca ar ramane datele vechi in cache
    const data = await this.questionService.deleteQuestion(this.question.id);
    this.router.navigate(['../'], {relativeTo: this.activatedRoute})
  }

  insertAnswer(answerText: string){
    if(answerText.length !== 0) {
      const newAnswer = new InsertAnswer(answerText, "assets/images/demo_photo.jpg", 1);
      this.answerService.insertAnswer(newAnswer, this.question.id).subscribe(
        answer => {
          this.answers.push(answer);
          this.toggleAnswerInsert();
        }
      );
    }
  }

  editAnswer(answerText: string, answerId: number) {
    this.answerService.editAnswer(answerId, answerText).subscribe(
      answer => console.log(answer)
    );

    this.toggleAnswerEdit();
  }
  /// request la BE doar atunci cand textul nou e diferit de cel initial

  deleteAnswer(answerId: number) {
    this.answerService.deleteAnswer(answerId).subscribe(
      answer => {
        // console.log(this.answers)
        //this.answers = this.answers.filter(ans => !(JSON.stringify(answer) === JSON.stringify(ans)));
        this.answers = this.answers.filter(ans => answer.id !== ans.id);
        // console.log(this.answers)
      }
    )
  }

}
