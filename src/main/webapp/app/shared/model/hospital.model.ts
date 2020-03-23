import { IAddress } from 'app/shared/model/address.model';
import { IUser } from 'app/core/user/user.model';
import { IBed } from 'app/shared/model/bed.model';

export interface IHospital {
  id?: string;
  name?: string;
  address?: IAddress;
  headOfSearvice?: IUser;
  beds?: IBed[];
}

export class Hospital implements IHospital {
  constructor(public id?: string, public name?: string, public address?: IAddress, public headOfSearvice?: IUser, public beds?: IBed[]) {}
}
