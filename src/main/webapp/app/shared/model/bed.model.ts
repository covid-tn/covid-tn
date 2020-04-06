import { IHospital } from 'app/shared/model/hospital.model';
import { BedStatus } from 'app/shared/model/enumerations/bed-status.model';

export interface IBed {
  id?: string;
  status?: BedStatus;
  name?: string;
  hospital?: IHospital;
}

export class Bed implements IBed {
  constructor(public id?: string, public status?: BedStatus, public name?: string, public hospital?: IHospital) {}
}
