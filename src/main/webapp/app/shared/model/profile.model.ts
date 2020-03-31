import { IHospital } from 'app/shared/model/hospital.model';

export interface IProfile {
  pin?: string;
  hospital?: IHospital;
}

export class Profile implements IProfile {
  constructor(public pin?: string, public hospital?: IHospital) {}
}
