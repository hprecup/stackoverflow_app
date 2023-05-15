import { User } from "./user";

export class Answer {
  constructor(public id: number,
              public text: string,
              public user: User,
              public picture: string,
              public creationDateTime: Date,
              public voteCount: number,
              public canBeModified: boolean,
              public canBeVoted: boolean,
              public voteType: string){}
}
