import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-kweet',
  templateUrl: './kweet.component.html',
  styleUrls: ['./kweet.component.css']
})
export class KweetComponent implements OnInit {

  @Input() public kweet : Kweet;
  constructor() { }

  ngOnInit() {
  }

}
