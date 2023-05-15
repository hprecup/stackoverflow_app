export class SessionData {
  constructor(public username: string,
              public userRoles: string[],
              public accessToken: string){}
}
