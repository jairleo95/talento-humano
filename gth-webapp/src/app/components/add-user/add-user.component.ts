import { User } from './../../models/user.model';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  user: User = {
    id: '',
    username: '',
    email: '',
    enabled: false
  };
  submitted = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  saveUser(): void {
    const data = {
      username: this.user.username,
      email: this.user.email
    };

    this.userService.create(data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.submitted = true;
        },
        error: (e) => console.error(e)
      });
  }

  newUser(): void {
    this.submitted = false;
    this.user = {
      username: '',
      email: '',
      enabled: false
    };
  }

}
