const ex = require('express');
const app = express();
const ax = require("axios")
var body_parser = require('body-parser').json();

const Puerto = 7003;
const Host = 'localhost';

app.listen(Puerto, Host);
console.log(`Corre Restaurante`);

app.get('/', function(req, res) {
    res.send("Restaurante")
});

app.get('/state',body_parser,function(req,res){
    var state = Math.floor(Math.random() * 3)
    if(state==1){
        descripcion = "enviada"
    }else if(state == 2){
        descripcion ="cancelada"
    }else{
        descripcion = "retrasada"
    }
    res.send(state)
});

app.get('/orden', function(req,res){
    
	var orden = req.body.id
    var descripcion = "Se recibio una orden con id:"+order
    ax.post('http://localhost:7002/cli/post',{'orden':orden})
	ax.post('http://localhost:7002/rep/post',{'orden':orden})
    res.send("OK")
	var orden = req.params.order

});

app.get('/delivery',body_parser,function(req,res){
    var orden = req.body.id
    var deliveryman = Math.floor(Math.random() * 5000)
    ax.post('http://localhost:7002/rep/postorder',{'id':orden})
    res.json({'empleado':empleado})
});
	