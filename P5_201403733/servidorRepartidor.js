const ex = require('express');
const app = express();
const ax = require("axios")
var body_parser = require('body-parser').json();

const Puerto = 7004;
const Host = 'localhost';

app.listen(Puerto, Host);
console.log(`Corre Repartidor`);

app.get('/', function(req, res) {
    res.send("Rpartidor")
});

app.get('/ordencli',body_parser,function(req,res){
    var order = req.params.order
    var state = Math.floor(Math.random() * 2)
    if(state==1){
        descripcion = "en camino"
    }else{
        descripcion = "entregada"
    }
	else{
        descripcion = "retrasada"
    }
    axios.post('http://localhost:7002/cli/post',{'orden':orden})
    res.send(descripcion)
});

app.get('/ordenres', function(req,res){
	var orden = req.body.id
    ax.post('http://localhost:7002/res/post',{'orden':orden})
    res.send("OK")
});

app.get('/delivery',body_parser,function(req,res){
    var order = req.body.id
    axios.post('http://localhost:7002/cli/post',{'orden':orden})
    res.send("OK")
});
	