import { UserService } from './../../services/user.service';
import { User } from './../../models/user.model';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  @Input() viewMode = false;

  @Input() currentUser: User = {
    username: '',
    email: '',
    enabled: false
  };

  constructor(
    private userService: UserService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    if (!this.viewMode) {
      this.getUser(this.route.snapshot.params["id"]);
    }
  }

  getUser(id: string): void {
    this.userService.get(id)
      .subscribe({
        next: (data) => {
          this.currentUser = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

}
