import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MenuComponent } from '../menu/menu.component';

@Component({
  selector: 'app-add-branches',
  standalone: true,
  templateUrl: './add-new-branch.component.html',
  styleUrls: ['./add-new-branch.component.css'],
  imports: [CommonModule, FormsModule, ReactiveFormsModule, MenuComponent]
})
export class AddNewBranchComponent implements OnInit {
  registerForm: FormGroup;
  cars: any[] = [];
  rentals: any[] = [];
  branchId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.registerForm = this.fb.group({
      branchId: [''],
      name: ['', Validators.required],
      address: ['', Validators.required],
      carList: [''],
      rentalId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.fetchRentals();
    this.fetchCars();

    this.route.queryParams.subscribe(params => {
      if (params['branchId']) {
        this.branchId = +params['branchId'];
        this.loadBranchDetails(this.branchId);
      }
    });
  }

  fetchCars() {
    this.http.get<any[]>('http://localhost:8080/api/car')
      .subscribe(data => {
        this.cars = data;
      }, error => {
        console.error('Error fetching cars:', error);
      });
  }

  fetchRentals() {
    this.http.get<any[]>('http://localhost:8080/api/rentals')
      .subscribe(data => {
        this.rentals = data;
      }, error => {
        console.error('Error fetching rentals:', error);
      });
  }

  loadBranchDetails(branchId: number) {
    this.http.get<any>(`http://localhost:8080/api/branch/${branchId}`).subscribe(branch => {

      const carIds = branch.carList.map((car: any) => car.carId);
      this.registerForm.patchValue({
        name: branch.name,
        address: branch.address,
        carList: carIds,
        rentalId: branch.rental.id
      });
    }, error => {
      console.error('Error fetching car details:', error);
    });
  }


  redirectToBranches() {
    this.router.navigate(['/branches']);
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.registerForm.patchValue({
        branchId: this.branchId});
      console.log('Sending data:', JSON.stringify(this.registerForm.value));


        this.http.put(`http://localhost:8080/api/addBranch`, this.registerForm.value, {
          headers: { 'Content-Type': 'application/json' }
        }).subscribe(
          response => {
            console.log('Car updated:', response);
            this.redirectToBranches();
          },
          error => console.error('Error updating car:', error)
        );
    } else {
      console.log('Form is invalid:', this.registerForm.errors);
    }
  }

  getCars(){
    return this.cars;
  }

  getRentals(){
    return this.rentals;
  }
}
