export class SessionData {
  constructor(public username: string,
              public userRoles: string[],
              public banned: boolean,
              public accessToken: string){}
}
