const ex = require('express');
const app = express();
const ax = require("axios")
var body_parser = require('body-parser').json();

const Puerto = 7001;
const Host = 'localhost';

app.listen(Puerto, Host);
console.log(`Corre Cliente`);

app.get('/', function(req, res) {
    res.send("Cliente")
});

app.get('/state',body_parser,function(req,res){
    var orden = req.body.id
    ax.get('http://localhost:7002/res/state/'+orden)
        .then(function(response){
            res.send(response['data'])
        })l
});

app.get('/orden', function(req,res){
    var orden = Math.floor(Math.random() * 99)
    ax.post('http://localhost:7002/res/postorden',{'id':orden})
    res.json({'id':orden})    
});

app.get('/delivery',body_parser,function(req,res){
    var order = req.body.id
    ax.get('http://localhost:7002/rep/state/'+orden)
    .then(function(response){
        res.send(response['data'])
    });
	
});
	