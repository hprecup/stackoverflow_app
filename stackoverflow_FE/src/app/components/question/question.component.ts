import { Component, OnInit, importProvidersFrom } from '@angular/core';
import { MaterialModule } from 'src/app/material/material.module';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Question} from '../../common/question';
import { QuestionService } from '../../services/question.service';
import { User } from '../../common/user';
import { Answer } from '../../common/answer';
import { InsertAnswer } from '../../common/insert-answer';
import { AnswerService } from '../../services/answer.service';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { VoteService } from 'src/app/services/vote.service';
import { Observable, finalize, map } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css'],
  imports:[
    MaterialModule,
    CommonModule,
    RouterModule,
    ReactiveFormsModule
  ]
})
export class QuestionComponent implements OnInit{

  //questionContent: string = "În conformitate cu prevederile legale, cu regulamentele și instrucțiunile în vigoare, revizorul școlar efectua inspecții și anchete, conducea conferința anuală a învățătorilor programată vara, lua parte la diferite manifestări culturale, sărbători legale și naționale etc. Revizorul I.L. Caragiale a procedat în activitatea sa în funcție de condițiile existente în județul Argeș.În perioada revizoratului școlar, dramaturgul Ion Luca Caragiale a locuit în Pitești, în cea mai mare parte a timpului, la hotelul-restaurant „Dacia” din strada Șerban-Vodă, local central de local de categoria I, cu saloane și separeuri în restaurant, cu sală de spectacole, club, bar, cafenea, cofetărie, grădină de vară cu scenă și ring de dans. Localul se situa la nivelul celor similare din București, Hotelul „Dacia” era renumit și prin spectacolele de varietăți susținute de actori profesioniști români și străini.";

  answerDate: Date = new Date();

  question: Question = new Question(0, "Not an existing question", "", "", new Date(), [], new User(0, "", "", "", "", 0, []), 0, false, false, "UNVOTED");

  questionText: string = "";

  answers: Answer[] = [];

  disableQuestionEditing: boolean = true;
  disableAnswerEditing: boolean = true;

  enableAnswerInsert: boolean = false;

  fileName = '';

  loading: boolean = true;

  answerInsertFormGroup: FormGroup =  new FormGroup({
    answerText: new FormControl("", [Validators.required])
  });

  constructor(public activatedRoute: ActivatedRoute, private questionService: QuestionService,
    private answerService: AnswerService, private voteService: VoteService, private router: Router) {
  }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    this.fetchQuestion();
    this.fetchAnswers();
  }

  fetchQuestion() {
    const questionId: number = +this.activatedRoute.snapshot.paramMap.get('id')!;
    this.loading = true;
    this.questionService.getQuestion(questionId)//.pipe(finalize(() => (this.loading = false)))
      .subscribe(
      question => {
        console.log(question)
        this.question = question
        this.questionText = question.text
      }
    );
  }

  fetchAnswers() {
    const questionId: number = +this.activatedRoute.snapshot.paramMap.get('id')!;
    this.questionService.getQuestionAnswers(questionId).subscribe(
      answers => {
        // console.log(answers)
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
    const data = await this.questionService.deleteQuestion(this.question.id);
    this.router.navigate(['../'], {relativeTo: this.activatedRoute})
  }

  insertAnswer(){
    if(this.answerInsertFormGroup.valid && this.fileName!=='') {
      let answerText = this.answerInsertFormGroup.get('answerText')?.value;
      const newAnswer = new InsertAnswer(answerText, "assets/images/"+this.fileName);
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

  upvoteQuestion(){
    this.voteService.upvoteQuestion(this.question.id).subscribe(
      question => {
        this.question = question
        this.updateUserAnswers(question.user.score);
      }
    );
  }

  downvoteQuestion(){
    this.voteService.downvoteQuestion(this.question.id).subscribe(
      question => {
        this.question = question
        this.updateUserAnswers(question.user.score);
      }
    );
  }

  updateUserAnswers(newScore: number) {
    for(let answer of this.answers){
      if(this.question.user.id === answer.user.id){
        answer.user.score = newScore;
      }
    }
  }

  upvoteAnswer(answerId: number) {
    this.voteService.upvoteAnswer(answerId).subscribe(
      answer => {
        this.updateUserScoreForQuestion(answer);
        this.updateUserScoreForAnswers(answer);
        this.sortAnswers();
      }
    )
  }

  downvoteAnswer(answerId: number) {
    this.voteService.downvoteAnswer(answerId).subscribe(
      answer => {
        console.log(answer);
        this.updateUserScoreForQuestion(answer);
        this.updateUserScoreForAnswers(answer);
        this.sortAnswers();
      }
    )
  }

  updateUserScoreForAnswers(answer: Answer) {
    let currentUsername = JSON.parse(sessionStorage.getItem("currentUser")!).username;
    this.answers = this.answers.map(ans => {
      if(ans.id === answer.id) {
        return answer;
      }else if(ans.user.id === answer.user.id) {
        ans.user.score = answer.user.score;
        return ans;
      }
      return ans;
    });
  }

  updateUserScoreForQuestion(answer: Answer) {
    if(answer.user.id === this.question.user.id){
      this.question.user.score = answer.user.score;
    }
  }

  sortAnswers(){
    this.answers = this.answers.sort((ans1, ans2) => ans2.voteCount - ans1.voteCount);
  }

}
