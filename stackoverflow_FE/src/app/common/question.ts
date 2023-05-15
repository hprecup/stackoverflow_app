import { User } from "./user";

export class Question {
  constructor(public id: number,
              public title: string,
              public text: string,
              public picture: string,
              public creationDateTime: Date,
              public tagNames: string[],
              public user: User,
              public voteCount: number,
              public canBeModified: boolean,
              public canBeVoted: boolean,
              public voteType: string) {
  }
}
