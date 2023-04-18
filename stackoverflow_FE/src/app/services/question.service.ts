import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Question } from '../common/question';
import { Tag } from '../common/tag';
import { InsertQuestion } from '../common/insert-question';
import { Answer } from '../common/answer';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private baseUrl = 'http://localhost:8080/question';

  private baseUrlTag = 'http://localhost:8080/tag';


  constructor(private httpClient: HttpClient) {

  }

  getQuestionList(): Observable<Question[]> {
    const searchUrl = `${this.baseUrl}/getAll`;
    return this.httpClient.get<Question[]>(searchUrl);
  }

  getQuestion(id: number) : Observable<Question> {
    const searchUrl = `${this.baseUrl}/get/${id}`;
    return this.httpClient.get<Question>(searchUrl);
  }

  getTags() : Observable<Tag[]> {
    return this.httpClient.get<Tag[]>(this.baseUrlTag);
  }

  addTag(tagName: string) : Observable<Tag> {
    return this.httpClient.post<Tag>(this.baseUrlTag, tagName);
  }

  insertQuestion(newQuestion: InsertQuestion) : Observable<Question>{
    const insertUrl = `${this.baseUrl}/insert`
    return this.httpClient.post<Question>(insertUrl, newQuestion)
  }

  editQuestion(id: number, newText: string) : Observable<Question> {
    const editUrl = `${this.baseUrl}/edit/${id}`
    return this.httpClient.put<Question>(editUrl, newText);
  }

  // deleteQuestion(id: number) : Observable<any> {
  //   const deleteUrl = `${this.baseUrl}/delete/${id}`;
  //   return this.httpClient.delete(deleteUrl);
  // }

  async deleteQuestion(id: number): Promise<any> {
    const deleteUrl = `${this.baseUrl}/delete/${id}`;
    try {
      await this.httpClient.delete(deleteUrl).toPromise();
    } catch (error) {
      console.error('Failed to delete question:', error);
      throw error;
    }
  }

  searchQuestion(searchString: string): Observable<Question[]>{
    const searchUrl = `${this.baseUrl}/search/${searchString}`;
    return this.httpClient.get<Question[]>(searchUrl);
  }

  getQuestionAnswers(id: number): Observable<Answer[]> {
    const searchUrl = `${this.baseUrl}/answers/${id}`;
    return this.httpClient.get<Answer[]>(searchUrl);
  }

}
