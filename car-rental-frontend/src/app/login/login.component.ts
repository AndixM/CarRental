import {Component} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [ReactiveFormsModule],
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router, private authService: AuthService) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;

      this.authService.login(credentials).subscribe({
        next: (response) => {
          console.log('Backend response:', response);

          if (response.includes("SUCCESS")) {
            console.log('Redirecționare către /cars...');
            this.router.navigate(['/cars']);
          } else {
            console.error('Login failed: Unexpected response:', response);
            alert('Autentificare eșuată. Încearcă din nou.');
          }
        },
        error: (err) => {
          console.error('Request err:', err);
          alert('Invalid username or password');
        }
      });
    }
  }

  redirectToRegister() {
    this.router.navigate(['/register']);
  }
}

