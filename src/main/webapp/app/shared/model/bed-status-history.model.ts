import { Moment } from 'moment';
import { IBed } from 'app/shared/model/bed.model';
import { IUser } from 'app/core/user/user.model';

export interface IBedStatusHistory {
  id?: string;
  createdDate?: Moment;
  bed?: IBed;
  createdBy?: IUser;
}

export class BedStatusHistory implements IBedStatusHistory {
  constructor(public id?: string, public createdDate?: Moment, public bed?: IBed, public createdBy?: IUser) {}
}
