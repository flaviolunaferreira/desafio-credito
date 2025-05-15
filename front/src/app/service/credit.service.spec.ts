/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { CreditService } from './credit.service';

describe('Service: Credit', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CreditService]
    });
  });

  it('should ...', inject([CreditService], (service: CreditService) => {
    expect(service).toBeTruthy();
  }));
});
