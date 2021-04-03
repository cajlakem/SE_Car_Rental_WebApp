import {Component, OnInit, Output} from "@angular/core";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {CurrencyService} from "../../_services/currency.service";
import {EventEmitter} from "@angular/core";

const standardCurrency = 'EUR';

@Component({
  selector: 'app-currency-selection',
  templateUrl: './currency-selection.component.html'
})


export class CurrencySelectionComponent implements OnInit {
  @Output() onCurrencyValueChanged = new EventEmitter<any>();
  currencies: string[] = [];
  myControl = new FormControl();
  filteredCurrencies: Observable<string[]>;

  constructor(private currencyService: CurrencyService) {
  }

  ngOnInit(): void {
    this.currencyService.retrieveCurrencyCodes().subscribe(currencies => {
      this.currencies = currencies;
      this.onCurrencyValueChanged.emit(standardCurrency);
      this.myControl.setValue(standardCurrency);
      this.filteredCurrencies = this.myControl.valueChanges.pipe(
        startWith(''),
        map(value => this._filter(value))
      );
    })
  }

  changeCurrency(event: any) {
    this.onCurrencyValueChanged.emit(event)
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.currencies.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
  }

}
