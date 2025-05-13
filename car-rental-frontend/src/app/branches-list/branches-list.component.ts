import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';
import {MenuComponent} from '../menu/menu.component';

interface BranchResponse {
  id: number;
  name: string;
  address: string;
  carList: Car[];
  rental: any;
}

interface Car {
  model: string;
  brand: string;
}

@Component({
  selector: 'app-reservation-list',
  standalone: true,
  templateUrl: './branches-list.component.html',
  styleUrls: ['./branches-list.component.css'],
  imports: [CommonModule, MenuComponent]
})
export class BranchesListComponent {
  branches: BranchResponse[] = [];

  constructor(private http: HttpClient, private router: Router) {
    this.fetchBranches();
  }

  fetchBranches() {
    this.http.get<BranchResponse[]>('http://localhost:8080/api/branch')
      .subscribe(data => {
        this.branches = data;
        console.log(this.branches);
      }, error => {
        console.error('Error fetching branches:', error);
      });
  }

  deleteBranch(id: number, index: number) {
    if (confirm("Are you sure you want to delete this branch?")) {
      this.http.delete<any[]>('http://localhost:8080/api/deleteBranch/' + id)
        .subscribe(data => {
          this.branches.splice(index, 1);
        }, error => {
          confirm("Error at deleting")
        });
    }
  }

  editBranch(branchId: number) {
    console.log("branchId :" + branchId);
    this.router.navigate(['/add-branches'], { queryParams: { branchId: branchId } });
  }
}
