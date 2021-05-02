package fh.se.car.rental.fh.model.enums;

public enum CurrencyCode {
  /*This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.*/

    AUD("Australia Dollar"),
    BGN("Bulgaria Lev"),
    BRL("Brazil Real"),
    CAD("Canada Dollar"),
    CHF("Switzerland Franc"),
    CNY("China Yuan Renminbi"),
    CZK("Czech Republic Koruna"),
    DKK("Denmark Krone"),
    EUR("Euro Member Countries"),
    GBP("United Kingdom Pound"),
    HKD("Hong Kong Dollar"),
    HRK("Croatia Kuna"),
    HUF("Hungary Forint"),
    IDR("Indonesia Rupiah"),
    ILS("Israel Shekel"),
    INR("India Rupee"),
    ISK("Iceland Krona"),
    JPY("Japan Yen"),
    KRW("Korea (South) Won"),
    MXN("Mexico Peso"),
    MYR("Malaysia Ringgit"),
    NOK("Norway Krone"),
    NZD("New Zealand Dollar"),
    PHP("Philippines Peso"),
    PLN("Poland Zloty"),
    RON("Romania New Leu"),
    RUB("Russia Ruble"),
    SEK("Sweden Krona"),
    SGD("Singapore Dollar"),
    THB("Thailand Baht"),
    TRY("Turkey Lira"),
    USD("United States Dollar"),
    ZAR("South Africa Rand");

    private String description;

    CurrencyCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
