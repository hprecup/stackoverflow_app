export class InsertQuestion {
  constructor(public authorId: number,
              public title: string,
              public text: string,
              public picture: string,
              public tagIds: number[]){}
}
