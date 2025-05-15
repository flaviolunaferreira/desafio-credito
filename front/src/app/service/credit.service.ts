import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CreditService {
  private apiUrl = 'http://localhost:8080/api/v1/creditos';

  constructor(private http: HttpClient) {}

  getCreditsByNfse(numeroNfse: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${numeroNfse}`, {
      headers: new HttpHeaders({ 'X-Forwarded-For': '192.168.1.1' })
    });
  }

  addCredit(credit: any): Observable<any> {
    return this.http.post(this.apiUrl, credit, {
      headers: new HttpHeaders({ 'X-Forwarded-For': '192.168.1.1' })
    });
  }
}
