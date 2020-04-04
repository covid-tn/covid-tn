import { IHospital } from 'app/shared/model/hospital.model';

export interface IServiceHospital {
  id?: string;
  name?: string;
  description?: string;
  hospital?: IHospital;
}

export class ServiceHospital implements IServiceHospital {
  constructor(public id?: string, public name?: string, public description?: string, public hospital?: IHospital) {}
}
