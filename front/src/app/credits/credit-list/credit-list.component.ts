import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CreditService } from '../../service/credit.service'; // Corrigido
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-credit-list',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule, FormsModule, CommonModule],
  templateUrl: './credit-list.component.html',
  styleUrls: ['./credit-list.component.scss']
})
export class CreditListComponent implements OnInit {
  credits: any[] = [];
  searchNfse: string = '';
  newCredit = {
    numeroCredito: '',
    numeroNfse: '',
    valorIssqn: 0,
    dataConstituicao: new Date().toISOString().split('T')[0],
    tipoCredito: 'ISSQN',
    simplesNacional: false,
    aliquota: 0.05,
    valorFaturado: 0,
    valorDeducao: 0,
    baseCalculo: 0
  };

  constructor(private creditService: CreditService) {}

  ngOnInit() {
    this.searchCredits();
  }

  searchCredits() {
    if (this.searchNfse) {
      this.creditService.getCreditsByNfse(this.searchNfse).subscribe(data => this.credits = data);
    }
  }

  addCredit() {
    this.creditService.addCredit(this.newCredit).subscribe(data => {
      this.credits.push(data);
      this.newCredit = {
        numeroCredito: '',
        numeroNfse: '',
        valorIssqn: 0,
        dataConstituicao: new Date().toISOString().split('T')[0],
        tipoCredito: 'ISSQN',
        simplesNacional: false,
        aliquota: 0.05,
        valorFaturado: 0,
        valorDeducao: 0,
        baseCalculo: 0
      };
    });
  }
}
