import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreditService } from '../../service/credit.service';

@Component({
  selector: 'app-credit-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './credit-list.component.html',
  styleUrls: ['./credit-list.component.css']
})
export class CreditListComponent implements OnInit {
  creditForm: FormGroup;
  credits: any[] = [];

  constructor(private creditService: CreditService, private fb: FormBuilder) {
    this.creditForm = this.fb.group({
      numeroCredito: ['', [Validators.required, Validators.minLength(1)]],
      numeroNfse: ['', [Validators.required, Validators.minLength(1)]],
      dataConstituicao: ['', Validators.required],
      valorIssqn: [0, [Validators.required, Validators.min(0.01)]],
      tipoCredito: ['', Validators.required],
      simplesNacional: [false],
      aliquota: [0, [Validators.required, Validators.min(0)]],
      valorFaturado: [0, [Validators.required, Validators.min(0)]],
      valorDeducao: [0, [Validators.required, Validators.min(0)]],
      baseCalculo: [0, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.loadCredits();
  }

  loadCredits(): void {
    this.creditService.getCredits().subscribe({
      next: (data) => {
        this.credits = data;
      },
      error: (err) => {
        console.error('Erro ao carregar créditos:', err);
        alert('Erro ao carregar créditos: ' + (err.error?.message || 'Verifique a conexão com o servidor.'));
      }
    });
  }

  addCredit(): void {
    if (this.creditForm.invalid) {
      this.creditForm.markAllAsTouched();
      alert('Preencha todos os campos obrigatórios corretamente.');
      return;
    }

    const payload = {
      numeroCredito: this.creditForm.value.numeroCredito,
      numeroNfse: this.creditForm.value.numeroNfse,
      dataConstituicao: this.creditForm.value.dataConstituicao,
      valorIssqn: Number(this.creditForm.value.valorIssqn),
      tipoCredito: this.creditForm.value.tipoCredito,
      simplesNacional: this.creditForm.value.simplesNacional,
      aliquota: Number(this.creditForm.value.aliquota),
      valorFaturado: Number(this.creditForm.value.valorFaturado),
      valorDeducao: Number(this.creditForm.value.valorDeducao),
      baseCalculo: Number(this.creditForm.value.baseCalculo)
    };

    this.creditService.addCredit(payload).subscribe({
      next: (data) => {
        this.credits.push(data);
        this.creditForm.reset({
          numeroCredito: '',
          numeroNfse: '',
          dataConstituicao: '',
          valorIssqn: 0,
          tipoCredito: '',
          simplesNacional: false,
          aliquota: 0,
          valorFaturado: 0,
          valorDeducao: 0,
          baseCalculo: 0
        });
        alert('Crédito adicionado com sucesso!');
      },
      error: (err) => {
        console.error('Erro ao adicionar crédito:', err);
        alert('Erro ao adicionar crédito: ' + (err.error?.message || 'Verifique os dados e a conexão com o servidor.'));
      }
    });
  }
}
