import express from "express";

const app = express();

app.use(express.json());


app.get('/', function (req, res) {
    res.json({message:"Hello World"});
})

app.post('/', function (req, res) {
    res.json({message:"Hello World"});
})

app.get('/products', function (req, res) {
    res.json({message:"Products"});
})

app.get('/pippo', function (req, res) {
    res.send('<p>Hello World</p>');
})

app.listen(3000);