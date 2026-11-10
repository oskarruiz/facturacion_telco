import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  template: `
    <div class="app-container">
      <nav *ngIf="isAuthenticated()" class="navbar">
        <div class="nav-content">
          <h1 class="app-title">Facturación Telco</h1>
          <div class="nav-links">
            <a routerLink="/ventas" routerLinkActive="active">Venta de Recargas</a>
            <a routerLink="/reportes" routerLinkActive="active">Reportes</a>
            <span class="user-info">{{ getUserName() }}</span>
            <button (click)="logout()" class="btn-logout">Cerrar Sesión</button>
          </div>
        </div>
      </nav>
      
      <main class="main-content">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    .app-container {
      min-height: 100vh;
      background: #f5f5f5;
    }

    .navbar {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      padding: 15px 0;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    .nav-content {
      max-width: 1200px;
      margin: 0 auto;
      padding: 0 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .app-title {
      margin: 0;
      font-size: 24px;
      font-weight: 600;
    }

    .nav-links {
      display: flex;
      align-items: center;
      gap: 20px;
    }

    .nav-links a {
      color: white;
      text-decoration: none;
      padding: 8px 16px;
      border-radius: 4px;
      transition: background 0.3s;
    }

    .nav-links a:hover,
    .nav-links a.active {
      background: rgba(255, 255, 255, 0.2);
    }

    .user-info {
      padding: 8px 16px;
      background: rgba(255, 255, 255, 0.1);
      border-radius: 4px;
    }

    .btn-logout {
      background: rgba(255, 255, 255, 0.2);
      color: white;
      border: 1px solid rgba(255, 255, 255, 0.3);
      padding: 8px 16px;
      border-radius: 4px;
      cursor: pointer;
      transition: all 0.3s;
    }

    .btn-logout:hover {
      background: rgba(255, 255, 255, 0.3);
    }

    .main-content {
      padding: 20px;
    }

    @media (max-width: 768px) {
      .nav-content {
        flex-direction: column;
        gap: 15px;
      }

      .nav-links {
        flex-wrap: wrap;
        justify-content: center;
      }
    }
  `]
})
export class AppComponent {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  getUserName(): string {
    const user = this.authService.getUserInfo();
    return user ? user.nombreCompleto || user.username : '';
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
