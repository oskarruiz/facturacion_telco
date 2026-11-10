import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/venta.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  loading: boolean = false;
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    if (!this.username || !this.password) {
      this.errorMessage = 'Por favor completa todos los campos';
      return;
    }

    const credentials: LoginRequest = {
      username: this.username,
      password: this.password
    };

    this.loading = true;
    this.errorMessage = '';

    this.authService.login(credentials).subscribe({
      next: (response) => {
        console.log('Login exitoso:', response);
        this.router.navigate(['/ventas']);
      },
      error: (error) => {
        console.error('Error en login:', error);
        this.errorMessage = error.error?.mensaje || 'Credenciales inválidas';
        this.loading = false;
      }
    });
  }
}
