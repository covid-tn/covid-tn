export interface IAddress {
  id?: string;
  streetName?: string;
  city?: string;
  region?: string;
  postalCode?: string;
}

export class Address implements IAddress {
  constructor(public id?: string, public streetName?: string, public city?: string, public region?: string, public postalCode?: string) {}
}
