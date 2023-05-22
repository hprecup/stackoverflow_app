import { Role } from "./role";

export class User {
  constructor(public id: number,
              public firstName: string,
              public lastName: string,
              public username: string,
              public email: string,
              public score: number,
              public banned: boolean,
              public roles: Role[]) {}
}
