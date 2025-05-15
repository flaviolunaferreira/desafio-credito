import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreditService } from '../../service/credit.service';

@Component({
  selector: 'app-credit-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './credit-search.component.html',
  styleUrls: ['./credit-search.component.css']
})
export class CreditSearchComponent {
  searchType: 'nfse' | 'credito' = 'nfse';
  searchValue: string = '';
  searchResults: any[] = [];
  singleResult: any = null;

  constructor(private creditService: CreditService) {}

  searchCredits() {
    this.searchResults = [];
    this.singleResult = null;

    if (!this.searchValue.trim()) {
      alert('Digite um valor para pesquisa.');
      return;
    }

    if (this.searchType === 'nfse') {
      this.creditService.getCreditsByNfse(this.searchValue).subscribe({
        next: (data) => {
          this.searchResults = data;
          if (data.length === 0) {
            alert('Nenhum crédito encontrado para a NFS-e: ' + this.searchValue);
          }
        },
        error: (err) => {
          console.error('Erro ao consultar créditos:', err);
          alert('Erro ao consultar créditos: ' + (err.error?.message || 'Verifique a conexão com o servidor.'));
        }
      });
    } else {
      this.creditService.getCreditByNumeroCredito(this.searchValue).subscribe({
        next: (data) => {
          this.singleResult = data;
          if (!data) {
            alert('Nenhum crédito encontrado para o número: ' + this.searchValue);
          }
        },
        error: (err) => {
          console.error('Erro ao consultar crédito:', err);
          alert('Erro ao consultar crédito: ' + (err.error?.message || 'Verifique a conexão com o servidor.'));
        }
      });
    }
  }
}
