import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MenuComponent } from '../menu/menu.component';

@Component({
  selector: 'app-add-cars',
  standalone: true,
  templateUrl: './add-new-car.component.html',
  styleUrls: ['./add-new-car.component.css'],
  imports: [CommonModule, FormsModule, ReactiveFormsModule, MenuComponent]
})
export class AddNewCarComponent implements OnInit {
  registerForm: FormGroup;
  branches: any[] = [];
  statuses: any[] = [];
  selectedBranch: string = '';
  selectedStatus: string = '';
  carId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.registerForm = this.fb.group({
      carId: [''],
      brand: ['', Validators.required],
      model: ['', Validators.required],
      bodyType: ['', Validators.required],
      year: ['', Validators.required],
      color: ['', Validators.required],
      mileage: ['', Validators.required],
      amount: ['', Validators.required],
      branchId: ['', Validators.required],
      status: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.fetchBranches();
    this.fetchStatuses();

    this.route.queryParams.subscribe(params => {
      if (params['carId']) {
        this.carId = +params['carId']; // Convertim la număr
        this.loadCarDetails(this.carId);
      }
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

  fetchStatuses() {
    this.http.get<any[]>('http://localhost:8080/api/statuses')
      .subscribe(data => {
        this.statuses = data;
      }, error => {
        console.error('Error fetching statuses:', error);
      });
  }

  loadCarDetails(carId: number) {

    console.log(carId);
    this.http.get<any>(`http://localhost:8080/api/car/${carId}`).subscribe(car => {

      this.registerForm.patchValue({
        brand: car.brand,
        model: car.model,
        bodyType: car.bodyType,
        year: car.year,
        color: car.color,
        mileage: car.mileage,
        amount: car.amount,
        status: car.status,
        branchId: car.branch?.branchId
      });
    }, error => {
      console.error('Error fetching car details:', error);
    });
  }

  onBranchSelected(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.selectedBranch = target.value;
    this.registerForm.patchValue({ branchId: this.selectedBranch });
  }

  onStatusSelected(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.selectedStatus = target.value;
    this.registerForm.patchValue({ status: this.selectedStatus });
  }

  redirectToCars() {
    this.router.navigate(['/cars']);
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.registerForm.patchValue({
        carId: this.carId});
      console.log('Sending data:', JSON.stringify(this.registerForm.value));


        this.http.put(`http://localhost:8080/api/addCar`, this.registerForm.value, {
          headers: { 'Content-Type': 'application/json' }
        }).subscribe(
          response => {
            console.log('Car updated:', response);
            this.redirectToCars();
          },
          error => console.error('Error updating car:', error)
        );
    } else {
      console.log('Form is invalid:', this.registerForm.errors);
    }
  }

  getStatuses(){
    return this.statuses;
  }

  getBranches(){
    return this.branches;
  }
}
