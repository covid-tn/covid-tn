import { IServiceHospital } from 'app/shared/model/service-hospital.model';

export interface IServiceRoom {
  id?: string;
  name?: string;
  description?: string;
  serviceHospital?: IServiceHospital;
}

export class ServiceRoom implements IServiceRoom {
  constructor(public id?: string, public name?: string, public description?: string, public serviceHospital?: IServiceHospital) {}
}
