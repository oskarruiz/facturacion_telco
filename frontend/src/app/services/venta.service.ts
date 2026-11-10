import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VentaDTO, VentaResponse, Venta, ReporteDTO } from '../models/venta.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  private apiUrl = 'http://localhost:8080/api/ventas';

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

  realizarVenta(venta: VentaDTO): Observable<VentaResponse> {
    return this.http.post<VentaResponse>(`${this.apiUrl}/realizar`, venta, {
      headers: this.getHeaders()
    });
  }

  consultarVentas(fechaInicio?: string, fechaFin?: string): Observable<Venta[]> {
    let params = new HttpParams();
    if (fechaInicio) params = params.append('fechaInicio', fechaInicio);
    if (fechaFin) params = params.append('fechaFin', fechaFin);

    return this.http.get<Venta[]>(this.apiUrl, {
      headers: this.getHeaders(),
      params
    });
  }

  obtenerVenta(id: number): Observable<Venta> {
    return this.http.get<Venta>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }

  reportePorOperador(fechaInicio: string, fechaFin: string): Observable<ReporteDTO[]> {
    const params = new HttpParams()
      .append('fechaInicio', fechaInicio)
      .append('fechaFin', fechaFin);

    return this.http.get<ReporteDTO[]>(`${this.apiUrl}/reporte/operador`, {
      headers: this.getHeaders(),
      params
    });
  }

  reportePorVendedor(fechaInicio: string, fechaFin: string): Observable<ReporteDTO[]> {
    const params = new HttpParams()
      .append('fechaInicio', fechaInicio)
      .append('fechaFin', fechaFin);

    return this.http.get<ReporteDTO[]>(`${this.apiUrl}/reporte/vendedor`, {
      headers: this.getHeaders(),
      params
    });
  }
}
