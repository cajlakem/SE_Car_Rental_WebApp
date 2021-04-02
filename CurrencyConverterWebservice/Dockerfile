FROM php:7.4-apache
COPY . /CurrencyConverter
WORKDIR /CurrencyConverter
RUN apt-get update && apt-get install -y libxml2-dev --no-install-recommends
RUN docker-php-ext-install soap
CMD [ "php", "./SoapTestClient.php" ]