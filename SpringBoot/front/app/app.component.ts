import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "../shared/header/header.component";
import { FooterComponent } from "../shared/footer/footer.component";
import { SidenavComponent } from "../shared/sidenav/sidenav.component";

@Component({
    selector: 'app-root',
    imports: [RouterOutlet, HeaderComponent, FooterComponent, SidenavComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {
}
