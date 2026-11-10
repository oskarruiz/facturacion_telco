import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RecargaService } from '../../services/recarga.service';
import { VentaService } from '../../services/venta.service';
import { Operador, Recarga, VentaDTO, VentaResponse } from '../../models/venta.model';

@Component({
  selector: 'app-venta-recarga',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './venta-recarga.component.html',
  styleUrls: ['./venta-recarga.component.css']
})
export class VentaRecargaComponent implements OnInit {
  operadores: Operador[] = [];
  recargas: Recarga[] = [];
  recargasFiltradas: Recarga[] = [];
  
  operadorSeleccionado: number | null = null;
  recargaSeleccionada: number | null = null;
  numeroCelular: string = '';
  observaciones: string = '';
  
  loading: boolean = false;
  mensaje: string = '';
  mensajeTipo: 'success' | 'error' | '' = '';
  ultimaVenta: VentaResponse | null = null;

  constructor(
    private recargaService: RecargaService,
    private ventaService: VentaService
  ) {}

  ngOnInit(): void {
    this.cargarOperadores();
    this.cargarRecargas();
  }

  cargarOperadores(): void {
    this.recargaService.obtenerOperadores().subscribe({
      next: (data) => {
        this.operadores = data;
      },
      error: (error) => {
        console.error('Error al cargar operadores:', error);
        this.mostrarMensaje('Error al cargar operadores', 'error');
      }
    });
  }

  cargarRecargas(): void {
    this.recargaService.obtenerRecargasActivas().subscribe({
      next: (data) => {
        this.recargas = data;
        this.recargasFiltradas = data;
      },
      error: (error) => {
        console.error('Error al cargar recargas:', error);
        this.mostrarMensaje('Error al cargar recargas', 'error');
      }
    });
  }

  onOperadorChange(): void {
    if (this.operadorSeleccionado) {
      this.recargasFiltradas = this.recargas.filter(
        r => r.operador.idOperador === this.operadorSeleccionado
      );
    } else {
      this.recargasFiltradas = this.recargas;
    }
    this.recargaSeleccionada = null;
  }

  realizarVenta(): void {
    if (!this.validarFormulario()) {
      return;
    }

    const ventaDTO: VentaDTO = {
      idRecarga: this.recargaSeleccionada!,
      numeroCelular: this.numeroCelular,
      observaciones: this.observaciones || undefined
    };

    this.loading = true;
    this.ventaService.realizarVenta(ventaDTO).subscribe({
      next: (response) => {
        this.ultimaVenta = response;
        this.mostrarMensaje('¡Venta realizada exitosamente!', 'success');
        this.limpiarFormulario();
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al realizar venta:', error);
        this.mostrarMensaje(
          error.error?.mensaje || 'Error al realizar la venta',
          'error'
        );
        this.loading = false;
      }
    });
  }

  validarFormulario(): boolean {
    if (!this.recargaSeleccionada) {
      this.mostrarMensaje('Por favor selecciona una recarga', 'error');
      return false;
    }

    if (!this.numeroCelular || this.numeroCelular.length !== 10) {
      this.mostrarMensaje('El número celular debe tener 10 dígitos', 'error');
      return false;
    }

    if (!/^\d+$/.test(this.numeroCelular)) {
      this.mostrarMensaje('El número celular solo debe contener números', 'error');
      return false;
    }

    return true;
  }

  limpiarFormulario(): void {
    this.operadorSeleccionado = null;
    this.recargaSeleccionada = null;
    this.numeroCelular = '';
    this.observaciones = '';
    this.recargasFiltradas = this.recargas;
  }

  mostrarMensaje(mensaje: string, tipo: 'success' | 'error'): void {
    this.mensaje = mensaje;
    this.mensajeTipo = tipo;
    
    setTimeout(() => {
      this.mensaje = '';
      this.mensajeTipo = '';
    }, 5000);
  }

  getRecargaValor(idRecarga: number): number {
    const recarga = this.recargasFiltradas.find(r => r.idRecarga === idRecarga);
    return recarga ? recarga.valor : 0;
  }
}
