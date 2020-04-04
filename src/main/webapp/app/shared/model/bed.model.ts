import { IHospital } from 'app/shared/model/hospital.model';
import { IServiceRoom } from 'app/shared/model/service-room.model';
import { BedStatus } from 'app/shared/model/enumerations/bed-status.model';

export interface IBed {
  id?: string;
  status?: BedStatus;
  hospital?: IHospital;
  room?: IServiceRoom;
}

export class Bed implements IBed {
  constructor(public id?: string, public status?: BedStatus, public hospital?: IHospital, public room?: IServiceRoom) {}
}
