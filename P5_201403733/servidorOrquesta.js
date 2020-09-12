const ex = require('express');
const app = express();
const ax = require("axios")
var body_parser = require('body-parser').json();

const Puerto = 7002;
const Host = 'localhost';

app.listen(Puerto, Host);
console.log(`ESB`);

app.get('/', function(req, res) {
    res.send("ESB")
});

app.post('/repartidor/postorder',body_parser,function(req,res){
    var orden = req.body.orden
    ax.post('http://localhost:7003/postorden',{'orden':orden})
    res.send("OK")
});
	
app.get('/rep/state/:orden',body_parser, function(req,res){
    var orden = req.params.order
    ax.get('http://localhost:7003/getorden/'+order)
    .then(function(response){
        res.send(response['data'])
    });
});

app.post('/res/orden',body_parser,function(req,res){
    var order = req.body.id
    ax.post('http://localhost:7001/postorden',{'orden':order})
    res.send("OK")   
});

app.get('/res/state',body_parser, function(req,res){
    var ordenm = req.params.order
    axios.get('http://localhost:7001/state/'+orde2)
        .then(function(response){
            res.send(response['data'])
        });