import { isPlatformBrowser, NgClass } from '@angular/common';
import { ChangeDetectorRef, Component, inject, PLATFORM_ID } from '@angular/core';
import { ChartModule } from 'primeng/chart';
import { NavBar } from '../../components/nav-bar/nav-bar';
import { SideBar } from '../../components/side-bar/side-bar';

@Component({
  selector: 'app-statistics',
  imports: [ChartModule, SideBar, NavBar, NgClass],
  templateUrl: './statistics.html'
})
export class Statistics {
  sidebarItems: any = [];
  isOpen: boolean = true;
  data: any;
  options: any;

  platformId = inject(PLATFORM_ID);

  constructor(private cd: ChangeDetectorRef) {}

  ngOnInit() {
      this.initChart();
  }

  initChart() {
          if (isPlatformBrowser(this.platformId)) {
              const documentStyle = getComputedStyle(document.documentElement);
              const textColor = documentStyle.getPropertyValue('--p-text-color');
              const textColorSecondary = documentStyle.getPropertyValue('--p-text-muted-color');
              const surfaceBorder = documentStyle.getPropertyValue('--p-content-border-color');

              this.data = {
                  labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
                  datasets: [
                      {
                          label: 'Nombre de patients',
                          type : "line",
                          data: [65, 59, 80, 81, 56, 55, 40],
                          fill: false,
                          borderColor: documentStyle.getPropertyValue('--p-cyan-500'),
                          tension: 0.4
                      }
                  ]
              };

              this.options = {
                  maintainAspectRatio: false,
                  aspectRatio: 0.6,
                  plugins: {
                      legend: {
                          labels: {
                              color: textColor
                          }
                      }
                  },
                  scales: {
                      x: {
                          ticks: {
                              color: textColorSecondary
                          },
                          grid: {
                              color: surfaceBorder,
                              drawBorder: false
                          }
                      },
                      y: {
                          ticks: {
                              color: textColorSecondary
                          },
                          grid: {
                              color: surfaceBorder,
                              drawBorder: false
                          }
                      }
                  }
              };
              this.cd.markForCheck()
          }
    }

  initCharProfits(){

  }

}
