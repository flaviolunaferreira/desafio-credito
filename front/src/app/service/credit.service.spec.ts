import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CreditService {
  private apiUrl = 'http://localhost:8080/api/v1/creditos';

  constructor(private http: HttpClient) { }

  getCredits(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  addCredit(credit: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'X-Forwarded-For': '192.168.1.1' // Simula o IP do cliente
    });
    return this.http.post<any>(this.apiUrl, credit, { headers });
  }
}
