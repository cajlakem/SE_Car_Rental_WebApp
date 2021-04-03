FROM php:7.4-apache
COPY . /CurrencyConverter
WORKDIR /CurrencyConverter
RUN chmod +x /CurrencyConverter
RUN apt-get update && apt-get install -y libxml2-dev --no-install-recommends
RUN docker-php-ext-install soap
RUN echo 'ServerName localhost' >> /etc/apache2/apache2.conf
COPY . /var/www/html
RUN /etc/init.d/apache2 restart