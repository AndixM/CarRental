import {Component} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router, private http: HttpClient) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      address: ['', Validators.required],
      age: ['', [Validators.required, Validators.min(18)]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10,15}$')]],
      password: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      console.log('Sending data:', JSON.stringify(this.registerForm.value));

      this.http.post('http://localhost:8080/api/register', this.registerForm.value, {
        headers: {'Content-Type': 'application/json'}
      })
        .subscribe(
          response => console.log('User created:', response),
          error => console.error('Error creating user:', error)
        );
    } else {
      console.log('Form is invalid:', this.registerForm.errors);
    }
  }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }
}
