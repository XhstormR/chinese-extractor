const regexgen = require('regexgen');
const readline = require('readline');
const fs = require('fs');

let rl = readline.createInterface({
    input: fs.createReadStream('tlds.txt', 'utf-8'),
    crlfDelay: Infinity
});

let list = [];
rl.on('line', (line) => list.push(line));
rl.on('close', () => console.log(regexgen(list)));
