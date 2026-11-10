import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Recarga, Operador } from '../models/venta.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class RecargaService {
  private apiUrl = 'http://localhost:8080/api/recargas';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  obtenerRecargasActivas(): Observable<Recarga[]> {
    return this.http.get<Recarga[]>(`${this.apiUrl}/activas`, {
      headers: this.getHeaders()
    });
  }

  obtenerRecarga(id: number): Observable<Recarga> {
    return this.http.get<Recarga>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }

  obtenerRecargasPorOperador(idOperador: number): Observable<Recarga[]> {
    return this.http.get<Recarga[]>(`${this.apiUrl}/operador/${idOperador}`, {
      headers: this.getHeaders()
    });
  }

  obtenerOperadores(): Observable<Operador[]> {
    return this.http.get<Operador[]>(`${this.apiUrl}/operadores`, {
      headers: this.getHeaders()
    });
  }
}
