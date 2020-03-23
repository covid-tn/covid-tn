import { IUser } from 'app/core/user/user.model';
import { IHospital } from 'app/shared/model/hospital.model';

export interface IProfile {
  id?: string;
  pin?: string;
  user?: IUser;
  hospital?: IHospital;
}

export class Profile implements IProfile {
  constructor(public id?: string, public pin?: string, public user?: IUser, public hospital?: IHospital) {}
}
