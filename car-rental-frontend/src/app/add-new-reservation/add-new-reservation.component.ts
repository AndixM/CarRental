import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MenuComponent} from '../menu/menu.component';

@Component({
  selector: 'app-add-reservation',
  standalone: true,
  templateUrl: './add-new-reservation.component.html',
  styleUrls: ['./add-new-reservation.component.css'],
  imports: [CommonModule, FormsModule, ReactiveFormsModule, MenuComponent]
})
export class AddNewReservationComponent implements OnInit {
  registerForm: FormGroup;
  cars: any[] = [];
  users: any[] = [];
  branches: any[] = [];
  selectedCar: string = '';
  selectedBranch: string = '';
  reservationId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.registerForm = this.fb.group({
      reservationId: [''],
      dateOfBooking: ['', Validators.required],
      dateFrom: ['', Validators.required],
      dateTo: ['', Validators.required],
      returnDepartment: ['', Validators.required],
      amount: ['', Validators.required],
      carId: ['', Validators.required],
      userIdSelect: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.fetchCars();
    this.fetchBranches();
    this.fetchUsers();

    this.route.queryParams.subscribe(params => {
      if (params['reservationId']) {
        this.reservationId = +params['reservationId'];
        this.loadCarDetails(this.reservationId);
      }
    });
  }

  fetchCars() {
    this.http.get<any[]>('http://localhost:8080/api/car')
      .subscribe(data => {
        console.log(data);
        this.cars = data;
      }, error => {
        console.error('Error fetching statuses:', error);
      });
  }

  fetchUsers() {
    this.http.get<any[]>('http://localhost:8080/api/users')
      .subscribe(data => {
        console.log(data);
        this.users = data;
      }, error => {
        console.error('Error fetching statuses:', error);
      });
  }

  loadCarDetails(reservationId: number) {

    console.log(reservationId);
    this.http.get<any>(`http://localhost:8080/api/reservation/${reservationId}`).subscribe(car => {
      console.log('UserId from API:', car.userId);
      this.registerForm.patchValue({
        reservationId: car.id,
        dateOfBooking: car.date? car.date.split('T')[0] : '',
        dateFrom: car.dateFrom? car.dateFrom.split('T')[0] : '',
        dateTo: car.dateTo? car.dateTo.split('T')[0] : '',
        returnDepartment: car.returnDepartment,
        amount: car.amount,
        carId: car.id,
        userIdSelect:  car.userId ? Number(car.userId) : null
      })
      ;
    }, error => {
      console.error('Error fetching car details:', error);
    });

  }

  fetchBranches() {
    this.http.get<any[]>('http://localhost:8080/api/branch')
      .subscribe(data => {
        this.branches = data;
      }, error => {
        console.error('Error fetching branches:', error);
      });
  }

  redirectToReservations() {
    this.router.navigate(['/reservations']);
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.registerForm.patchValue({
        reservationId: this.reservationId
      });
      console.log('Sending data:', JSON.stringify(this.registerForm.value));

      this.registerForm.patchValue({
          dateOfBooking: this.formatDate(this.registerForm.value.dateOfBooking),
          dateFrom: this.formatDate(this.registerForm.value.dateFrom),
          dateTo: this.formatDateTime(this.registerForm.value.dateTo)
      });

      this.http.put(`http://localhost:8080/api/addReservation`, this.registerForm.value, {
        headers: {'Content-Type': 'application/json'}
      }).subscribe(
        response => {
          console.log('Car updated:', response);
          this.redirectToReservations();
        },
        error => console.error('Error updating car:', error)
      );
    } else {
      console.log('Form is invalid:', this.registerForm.errors);
    }
  }

  getCars() {
    return this.cars;
  }

  getUsers() {
    return this.users;
  }

  getBranches() {
    return this.branches;
  }

  formatDate(date: string): string | null {
    if (!date) return null;
    return new Date(date).toISOString().split('T')[0];
  }

  formatDateTime(date: string): string | null {
    if (!date) return null;
    return new Date(date).toISOString();
  }
}
