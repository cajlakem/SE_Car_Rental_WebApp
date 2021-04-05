using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Threading.Tasks;

namespace CurrencyConverterWebservice.Models
{
    [ServiceContract]
    public interface IConverterService
    {
        [OperationContract]
        double convert(string apiToken, string toCurrency, double amount);
    }
}
