import { HttpClient, HttpStatusCode } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { InsertAnswer } from '../common/insert-answer';
import { Answer } from '../common/answer';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  private baseUrl = 'http://localhost:8080/answer';

  constructor(private httpClient: HttpClient) {

  }

  insertAnswer(newAnswer: InsertAnswer, questionId: number): Observable<Answer> {
    const insertUrl = `${this.baseUrl}/insert/${questionId}`;

    return this.httpClient.post<Answer>(insertUrl, newAnswer);
  }

  editAnswer(id: number, newText: string): Observable<Answer> {
    const editUrl = `${this.baseUrl}/edit/${id}`;

    return this.httpClient.put<Answer>(editUrl, newText);
  }

  deleteAnswer(id: number) : Observable<Answer> {
    const deleteUrl = `${this.baseUrl}/delete/${id}`;

    return this.httpClient.delete<Answer>(deleteUrl);
  }

}
