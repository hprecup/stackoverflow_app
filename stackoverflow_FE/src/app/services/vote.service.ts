import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Question } from '../common/question';
import { Observable } from 'rxjs';
import { Answer } from '../common/answer';

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  private baseUrl = 'http://localhost:8080/vote';

  constructor(private httpClient: HttpClient) { }

  upvoteQuestion(questionId: number): Observable<Question> {
    const upvoteUrl = `${this.baseUrl}/question/upvote/${questionId}`;
    return this.httpClient.get<Question>(upvoteUrl);
  }

  downvoteQuestion(questionId: number): Observable<Question> {
    const downvoteUrl = `${this.baseUrl}/question/downvote/${questionId}`;
    return this.httpClient.get<Question>(downvoteUrl);
  }

  upvoteAnswer(answerId: number): Observable<Answer> {
    const upvoteUrl = `${this.baseUrl}/answer/upvote/${answerId}`;
    return this.httpClient.get<Answer>(upvoteUrl);
  }

  downvoteAnswer(answerId: number): Observable<Answer> {
    const downvoteUrl = `${this.baseUrl}/answer/downvote/${answerId}`;
    return this.httpClient.get<Answer>(downvoteUrl);
  }

}
