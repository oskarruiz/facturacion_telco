export interface VentaDTO {
  idRecarga: number;
  numeroCelular: string;
  observaciones?: string;
}

export interface VentaResponse {
  idVenta: number;
  referenciaTransaccion: string;
  valor: number;
  comision: number;
  nombreOperador: string;
  numeroCelular: string;
  fechaVenta: Date;
  estado: string;
  mensaje: string;
}

export interface Venta {
  idVenta: number;
  usuario: Usuario;
  recarga: Recarga;
  operador: Operador;
  numeroCelular: string;
  valor: number;
  comision: number;
  fechaVenta: Date;
  estado: string;
  referenciaTransaccion: string;
  observaciones?: string;
}

export interface Usuario {
  idUsuario: number;
  username: string;
  nombre: string;
  apellido: string;
  email: string;
  telefono?: string;
  rol: string;
}

export interface Operador {
  idOperador: number;
  nombre: string;
  codigo: string;
  descripcion?: string;
  activo: boolean;
}

export interface Recarga {
  idRecarga: number;
  operador: Operador;
  valor: number;
  descripcion?: string;
  comision: number;
  activo: boolean;
}

export interface ReporteDTO {
  nombre: string;
  nombreCompleto?: string;
  cantidadVentas: number;
  totalVentas: number;
  totalComisiones: number;
  fechaInicio: Date;
  fechaFin: Date;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  username: string;
  nombreCompleto: string;
  rol: string;
  mensaje: string;
}
