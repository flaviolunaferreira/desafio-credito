import { Routes } from '@angular/router';
import { CreditListComponent } from './credits/credit-list/credit-list.component';
import { CreditSearchComponent } from './credits/credit-search/credit-search.component';

export const routes: Routes = [
  { path: '', redirectTo: 'credits', pathMatch: 'full' },
  { path: 'credits', component: CreditListComponent },
  { path: 'search', component: CreditSearchComponent }
];
