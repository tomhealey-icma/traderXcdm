import * as fs from 'fs';
//const CsvReadableStream = require('csv-reader');
import { Stock } from '../stocks/interfaces/stock.interface';
import * as readline from 'readline';

export async function loadCsvData() {
  let tickerStr;
  let companyNameStr;

  const promise = new Promise<Stock[]>((resolve) => {
    const stocks: Stock[] = [];
    let isHeaderRow = true;
    //fs.createReadStream('./data/s-and-p-500-companies.csv', 'utf8')
    const rl = readline.createInterface({
      input: fs.createReadStream(
        './data/s-and-p-500-companies-cdm.csv',
        'utf8',
      ),
      crlfDelay: Infinity,
    });
    //.pipe(new CsvReadableStream({ trim: true }))
    rl.on('line', function (row) {
      if (isHeaderRow === true) {
        isHeaderRow = false;
      } else {
        const obj = JSON.parse(row);
        tickerStr =
          obj['product']['security']['productIdentifier'][0]['value'][
            'identifier'
          ]['value'];
        companyNameStr =
          obj['product']['security']['productIdentifier'][1]['value'][
            'identifier'
          ]['value'];
        //Get Data for stocks object.
        stocks.push({ ticker: tickerStr, companyName: companyNameStr });
      }
    });
    rl.on('close', function () {
      resolve(stocks);
    });
  });
  return promise;
}
