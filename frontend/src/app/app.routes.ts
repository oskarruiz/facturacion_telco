import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { VentaRecargaComponent } from './components/venta-recarga/venta-recarga.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'ventas', component: VentaRecargaComponent },
  { path: '**', redirectTo: '/login' }
];
