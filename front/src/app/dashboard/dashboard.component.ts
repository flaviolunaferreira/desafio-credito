import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { Chart, registerables } from 'chart.js';
import { CreditService } from '../service/credit.service';
import { CommonModule } from '@angular/common';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MatCardModule, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  constructor(private creditService: CreditService) {}

  ngOnInit() {
    this.creditService.getCreditsByNfse('NFSE456').subscribe(credits => {
      const states = this.groupByState(credits);
      this.renderChart('creditChart', states, 'Créditos');
    });
  }

  groupByState(credits: any[]) {
    const states = { pendente: 0, processado: 0, concluído: 0 };
    credits.forEach((credit, index) => {
      const state = index % 3 === 0 ? 'pendente' : index % 3 === 1 ? 'processado' : 'concluído';
      states[state]++;
    });
    return states;
  }

  renderChart(canvasId: string, data: any, label: string) {
    new Chart(canvasId, {
      type: 'pie',
      data: {
        labels: Object.keys(data),
        datasets: [{
          data: Object.values(data),
          backgroundColor: ['#bd93f9', '#8be9fd', '#ff79c6']
        }]
      },
      options: {
        plugins: { legend: { labels: { color: '#f8f8f2' } } }
      }
    });
  }
}
